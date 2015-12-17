import functools, re

def find_sue(comparison):
    with open('sues', 'r') as f:
        for sue in f:
            m, traits = re.search(r"^Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)", sue), {}
            sue_num, traits[m.group(2)], traits[m.group(4)], traits[m.group(6)] = m.group(1), int(m.group(3)), int(m.group(5)), int(m.group(7))
            if comparison(traits):
                return sue_num

hint = {'children': 3, 'cats': 7, 'samoyeds': 2, 'pomeranians': 3, 'akitas': 0, 'vizslas': 0, 'goldfish': 5, 'trees': 3, 'cars': 2, 'perfumes': 1}
print(find_sue(lambda traits, hint=hint: functools.reduce(lambda correct_sue, trait: correct_sue and trait[0] in hint and hint[trait[0]] == trait[1], traits.items(), True)))
print(find_sue(lambda traits, hint=hint: functools.reduce(lambda correct_sue, trait: correct_sue and trait[0] in hint and (hint[trait[0]] < trait[1] if trait[0] in ('cats', 'trees') else hint[trait[0]] > trait[1] if trait[0] in ('pomeranians', 'goldfish') else hint[trait[0]] == trait[1]), traits.items(), True)))
