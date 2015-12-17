with open('containers', 'r') as f:
	containers, combos = sorted([int(x) for x in f]), set()
	find_combos = lambda remaining_eggnog, used, combos=combos, containers=containers: combos.add(used) if not remaining_eggnog else any(find_combos(remaining_eggnog - container, used | 1 << i) for i, container in enumerate(containers) if container <= remaining_eggnog and not used & 1 << i)
	find_combos(150, 0)
	print(len(combos))
