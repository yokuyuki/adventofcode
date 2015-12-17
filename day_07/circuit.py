import collections, operator, re, sys

class Gate:
    def __init__(self, fn, incoming, outgoing):
        self.fn, self.incoming, self.outgoing = fn, incoming, outgoing
        self.incoming = incoming
        for wire in self.incoming:
            wire.connect(self)
        self.activate()

    def activate(self):
        if all(wire.signal is not None for wire in self.incoming):
            self.outgoing.set_signal(self.fn())

class Wire:
    def __init__(self):
        self.signal, self.outgoing = None, []

    def set_signal(self, signal):
        if signal != self.signal and (type(signal) == int or (type(signal) == str and signal.isnumeric())):
            self.signal = int(signal)
            for gate in self.outgoing:
                gate.activate()
        return self

    def connect(self, gate):
        self.outgoing.append(gate)

with open('connections', 'r') as f:
    wires, gates, ops = collections.defaultdict(Wire), {}, {'AND': operator.and_, 'OR': operator.or_, 'LSHIFT': operator.lshift, 'RSHIFT': operator.rshift}
    for connection in f:
        passthru = re.search(r"^(?P<inverter>NOT )?(?P<operand>\w+) -> (?P<out>\w+)", connection)
        combiner = re.search(r"^(\w+) (AND|OR|LSHIFT|RSHIFT) (\w+) -> (\w+)", connection)
        if passthru:
            operand, out, op = wires[passthru.group('operand')].set_signal(passthru.group('operand')), wires[passthru.group('out')], (lambda x: 65535 - x) if passthru.group('inverter') else (lambda x: x)
            gates[passthru.group(0)] = Gate(lambda op=op, operand=operand: op(operand.signal), [operand], out)
        elif combiner:
            a, b, out, op = wires[combiner.group(1)].set_signal(combiner.group(1)), wires[combiner.group(3)].set_signal(combiner.group(3)), wires[combiner.group(4)], ops[combiner.group(2)]
            gates[combiner.group(0)] = Gate(lambda op=op, a=a, b=b: op(a.signal, b.signal), [a, b], out)
    print(wires['a'].signal)
    wires['b'].set_signal(wires['a'].signal)
    print(wires['a'].signal)
