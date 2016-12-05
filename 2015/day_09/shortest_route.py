import re

def travel(distances, unvisited, visited, visited_distance):
	if len(unvisited):
		for city in unvisited:


with open('distances', 'r') as f:
	distances, paths, cities = {}, {}, set()
	for distance in f:
		m = re.search(r"^(\w+) to (\w+) = (\d+)")
		src, dest, dist = m.group(1), m.group(2), int(m.group(3))
		distances[(src, dest)], distances[(dest, src)] = dist, dist
		cities.update((src, dest))
	for city_pairs, distance in distances.items():
		visited = list(city_pairs)
