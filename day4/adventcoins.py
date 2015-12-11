import hashlib

def mine(leading_zeroes = 5):
	third_byte_less_than = 1 if leading_zeroes == 6 else 16
	base, i = hashlib.md5('yzbqklnj'.encode('utf-8')), 0
	while True:
		m, i = base.copy(), i+1
		m.update(str(i).encode('utf-8'))
		md5sum = m.digest()
		if md5sum[0] == md5sum[1] == 0 and md5sum[2] < third_byte_less_than:
			print(i)
			break

mine(5)
mine(6)
