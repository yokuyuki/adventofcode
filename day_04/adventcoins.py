from hashlib import md5

def mine(good_md5sum):
    base, i = md5('yzbqklnj'.encode('utf-8')), 0
    while True:
        m, i = base.copy(), i+1
        m.update(str(i).encode('utf-8'))
        md5sum = m.digest()
        if good_md5sum(md5sum):
            print(i)
            break

mine(lambda md5sum: True if md5sum[0] == md5sum[1] == (md5sum[2] & 0xf0) == 0 else False)
mine(lambda md5sum: True if md5sum[0] == md5sum[1] == md5sum[2] == 0 else False)
