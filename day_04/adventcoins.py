import hashlib, itertools

def mine(good_md5sum):
    base = hashlib.md5('yzbqklnj'.encode('utf-8'))
    for i in itertools.count():
        m = base.copy()
        m.update(str(i).encode('utf-8'))
        if good_md5sum(m.digest()): return i

print(mine(lambda md5sum: True if md5sum[0] == md5sum[1] == (md5sum[2] & 0xf0) == 0 else False))
print(mine(lambda md5sum: True if md5sum[0] == md5sum[1] == md5sum[2] == 0 else False))
