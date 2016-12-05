import re

def race(speed, endurance, recovery_time, time):
    iterations, partial_iteration_time = divmod(time, (endurance + recovery_time))
    travel_time = iterations * endurance + partial_iteration_time if partial_iteration_time < endurance else iterations * endurance + endurance
    return speed * travel_time

with open('reindeer_descriptions', 'r') as f:
    furthest = 0
    for description in f:
        m = re.search(r"^(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.", description)
        name, speed, endurance, recovery_time = m.group(1), int(m.group(2)), int(m.group(3)), int(m.group(4))
        distance = race(speed, endurance, recovery_time, 2503)
        furthest = distance if distance > furthest else furthest
    print(furthest)
