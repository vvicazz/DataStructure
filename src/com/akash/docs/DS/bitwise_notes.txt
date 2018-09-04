and		&
or		|
xor		^

positive number
negative number

left shift
	->positive
	->negative
right shift
	->positive
	->negative
	
---------------------------

get negative of a number:
1. find its one's complement by reversing all digits
2. find its two's complement by adding 1 to it.

(Q)	XOR property
	0^n=n
	n^n=0
(Q)if in a list of numbers n1,n2,n3,n4,n4...
 	all numbers have even occurense except one,then to find that number
	n1 ^ n2 ^ n3 ^ n4
(Q)reverse sign of a number	eg: in=-10 , out=10 
 	~n+1
 	2's compliment
 
(Q) return 1 if 0, return 0 if 1
->	1-n
->	1^n
->	~n+1+1



=>Shift Operators
case 1 :
10111111111111111111111111111111  >> 2 = 11101111111111111111111111111111
right shift holds right most bit value
hence, it can be used for division for both negative and positive

case 2 :
11111111111111111111111111111111 << 2 = 11111111111111111111111111111100
left shift does not hold left most bit value

case 3 :
10111111111111111111111111111111  >>> 2 = 00101111111111111111111111111111
triple right shift does not hold right most bit value

