import json

def count(things):
    if type(things) == list or (type(things) == dict and all(thing != 'red' for thing in things.values())):
        return sum(count(thing) for thing in (things if type(things) == list else things.values()))
    return things if type(things) == int else 0

with open('document', 'r') as f:
    print(count(json.loads(f.read())))
