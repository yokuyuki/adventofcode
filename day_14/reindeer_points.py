import collections, operator, re

class Reindeer:
    def __init__(self, speed, endurance, recovery_time):
        self.speed, self.endurance, self.recovery_time = speed, endurance, recovery_time
        self.distance, self.stamina, self.recovery = 0, endurance, 0

    def tick(self):
        if self.stamina > 0:
            self.distance += self.speed
            self.stamina -= 1
            self.recovery = self.recovery_time if self.stamina == 0 else self.recovery
        else:
            self.recovery -= 1
            self.stamina = self.endurance if self.recovery == 0 else self.stamina
        return self.distance

with open('reindeer_descriptions', 'r') as f:
    reindeers, points = {}, collections.defaultdict(int)
    for description in f:
        m = re.search(r"^(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.", description)
        name, speed, endurance, recovery_time = m.group(1), int(m.group(2)), int(m.group(3)), int(m.group(4))
        reindeers[name] = Reindeer(speed, endurance, recovery_time)
    for i in range(2503):
        distance_pairs = sorted([(name, reindeer.tick()) for name, reindeer in reindeers.items()], key=operator.itemgetter(1), reverse=True)
        for name, distance in ((name, distance) for name, distance in distance_pairs if distance == distance_pairs[0][1]):
            points[name] += 1
    print(sorted(points.values(), reverse=True)[0])
