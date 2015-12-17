import re

def find_sue(comparison):
    with open('sues', 'r') as f:
        for sue in f:
            m, traits = re.search(r"^Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)", sue), {}
            sue_num, traits[m.group(2)], traits[m.group(4)], traits[m.group(6)] = m.group(1), int(m.group(3)), int(m.group(5)), int(m.group(7))
            if comparison(traits):
                return sue_num

hint = {'children': 3, 'cats': 7, 'samoyeds': 2, 'pomeranians': 3, 'akitas': 0, 'vizslas': 0, 'goldfish': 5, 'trees': 3, 'cars': 2, 'perfumes': 1}
print(find_sue(lambda traits, hint=hint: all(trait in hint and hint[trait] == num for trait, num in traits.items())))
print(find_sue(lambda traits, hint=hint: all(trait in hint and hint[trait] < num if trait in ('cats', 'trees') else hint[trait] > num if trait in ('pomeranians', 'goldfish') else hint[trait] == num for trait, num in traits.items())))
