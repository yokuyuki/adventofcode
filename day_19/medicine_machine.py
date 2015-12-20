from collections import defaultdict

with open('calibration', 'r') as f:
    conversions, molecule = defaultdict(list), []
    for line in f:
        split_line = line.rstrip().split(' => ')
        if len(split_line) == 2:
            conversions[split_line[0]].append(split_line[1])
        elif line:
            molecule = [line[i] if not line[i+1].islower() else line[i] + line[i+1] for i in range(len(line)) if line[i].isupper() or line[i].isdigit()]
    distinct_molecules = set(''.join(molecule[0:i]) + replacement + ''.join(molecule[i+1:]) for i in range(len(molecule)) for replacement in conversions[molecule[i]])
    print(len(distinct_molecules))
