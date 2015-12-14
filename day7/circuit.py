import collections, functools, operator, re, sys

class Gate:
    def __init__(self, fn, incoming, outgoing):
        self.fn = fn
        self.incoming = incoming
        for wire in self.incoming:
            wire.connect(self)
        self.outgoing = outgoing
        self.activate()

    def activate(self):
        if functools.reduce(lambda can_activate, wire: can_activate and wire.get_signal() is not None, self.incoming, True):
            self.outgoing.set_signal(self.fn())

class Wire:
    def __init__(self, signal=None):
        self.signal = signal
        self.outgoing = []

    def get_signal(self):
        return self.signal

    def set_signal(self, signal):
        if signal == self.signal or (type(signal) == str and not signal.isnumeric()): return self
        self.signal = int(signal)
        for gate in self.outgoing:
            gate.activate()
        return self

    def connect(self, gate):
        self.outgoing.append(gate)

    def __repr__(self):
        return str(self.signal)

with open('connections', 'r') as f:
    wires, gates, ops = collections.defaultdict(Wire), {}, {'AND': operator.and_, 'OR': operator.or_, 'LSHIFT': operator.lshift, 'RSHIFT': operator.rshift}
    for connection in f:
        direct = re.search(r"^(\w+) -> (\w+)", connection)
        combiner = re.search(r"^(\w+) (AND|OR) (\w+) -> (\w+)", connection)
        shifter = re.search(r"^(\w+) (LSHIFT|RSHIFT) (\d+) -> (\w+)", connection)
        inverter = re.search(r"^NOT (\w+) -> (\w+)", connection)
        if direct:
            operand, out = wires[direct.group(1)].set_signal(direct.group(1)), wires[direct.group(2)]
            gates[direct.group(0)] = Gate(lambda operand=operand: operand.get_signal(), [operand], out)
        elif combiner:
            a, b, out, op = wires[combiner.group(1)].set_signal(combiner.group(1)), wires[combiner.group(3)].set_signal(combiner.group(3)), wires[combiner.group(4)], ops[combiner.group(2)]
            gates[combiner.group(0)] = Gate(lambda op=op, a=a, b=b: op(a.get_signal(), b.get_signal()), [a, b], out)
        elif shifter:
            a, b, out, op = wires[shifter.group(1)], int(shifter.group(3)), wires[shifter.group(4)], ops[shifter.group(2)]
            gates[shifter.group(0)] = Gate(lambda op=op, a=a, b=b: op(a.get_signal(), b), [a], out)
        elif inverter:
            operand, out = wires[inverter.group(1)], wires[inverter.group(2)]
            gates[inverter.group(0)] = Gate(lambda operand=operand: 65535 - operand.get_signal(), [operand], out)
    print(wires['a'])
    wires['b'].set_signal(wires['a'].get_signal())
    print(wires['a'])
