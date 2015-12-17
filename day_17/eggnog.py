def find_combos(combos, containers, remaining_eggnog=150, used=0):
	if remaining_eggnog == 0:
		combos.add(used)
	else:
		for i, container in ((i, container) for i, container in enumerate(containers) if container <= remaining_eggnog and not used & 1 << i):
			find_combos(combos, containers, remaining_eggnog - container, used | 1 << i)

with open('containers', 'r') as f:
	containers, combos = sorted([int(x) for x in f]), set()
	find_combos(combos, containers)
	print(len(combos))
