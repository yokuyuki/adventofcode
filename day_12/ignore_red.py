import functools, json

def count(things):
    if type(things) == list or (type(things) == dict and all(thing != 'red' for thing in things.values())):
        return functools.reduce(lambda sum, thing: sum + count(thing), things if type(things) == list else things.values(), 0)
    return things if type(things) == int else 0

with open('document', 'r') as f:
    document = json.loads(f.read())
    print(count(document))
