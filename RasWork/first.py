# 여기는 주석문입니다
'''
print('Hello World');
a = 50
b = 40
sum = a + b
print(sum);
print(a-b);
sum=a-b
print(sum);
print(2**3);


a=60
print(a+b);

sum = a/b
print(sum);
print(a//b);'''

'''
names=['mike','jane','tom']
print(names);
print(names.index('jane'))
names.append('kal');
print(names);
print('-----------');

print(names.insert(1,'ruye13'));
print(names.pop());
print(names);

print('-----------');

names.insert(1,'ruye13');
names.pop();
print(names);


print('-----------');


names.append('mike')
print(names.count('mike'))

print('-----------');
print('-----------');


print(names.append('b'))
print(names);

print('-----------');

names.append('b')
print(names);'''








'''

num_list=[8,3,5,7,2,1]
print(num_list)
print('-----------');
print('-----------');

print(num_list.sort())
print(num_list)

print('-----------');

num_list.sort()
print(num_list)
print('-----------');

num_list.reverse()
print(num_list)
num_list.clear()
print(num_list)

print('-----------');
print('-----------');
user_data=[20,'mike','seoul',True]
print(user_data)

print('-----------');
num_list.extend(user_data)
print(num_list)
      
'''















'''

user={'age':25, 'address':'seoul'}
print(user)

print('-----------');

print(user['age'])
print(user.get('age'))


print('-----------');


print(user.get('name','possible'))


print('+++++++++++++');


user['age']=28
user['name']='kim'
print(user)


print('=========');

print(user.keys())
print(user.values())
print(user.items())
user.clear()
print(user)
'''



'''

a="Good morning, man"

index=0
index=a.index('m', index)
print(index)
index=a.index('m', index+1)
print(index)

'''






'''
test3="""
I am a student,
Python is awesome easy
"""
print(test3)

'''






















'''
print("a"+"b")
print("a","b")

print("I am %d years old." %20)

print("I like {0} color and {1} color.".format("blue","red"))




age=20
color="red"
print(f"I am {age} years old and like {color} color")
'''


'''
jumin="123456-1234567"
print(jumin[-7:])
print(jumin[7])
print(jumin[0:2])
'''

'''
a=0
while a < 10 :
    a = a+1
    print(a)
    
'''


'''
number=[1,2,3,4,5,6,7]
for num in number:
    if num%2==0:
        print(num)'''



'''

absent=[2,5]
no_book=[8]
for student in range(1,11):
    if student in absent:
        continue
    elif student in no_book:
        print("The end. {0}buy coffee".format(student))
        break
    print("{0},Read the book".format(student))'''






'''
students=[1,2,3,4,5]
print(students)
students=[i+100 for i in students]
print(students)


students=["Iron man","Thor","I am groot"]
students2=[len(i) for i in students]
print(students)

students3=[i.upper() for i in students]
print(students3)'''










'''
for x in range(2, 10):
    print("-----["+str(x)+"단]-----")
    for y in range(1, 10):
        print(x, "X", y, "=", x*y)
print("----------------------")
'''



'''
import time
for i in range(10):
    print(i)
    time.sleep(0.1)
'''




'''
def mySum1():
    a=10
    b=20
    print(a+b)
    
mySum1()  '''





def mySum2():
    a=10
    b=20
    return a+b, a-b, a*b
    
print(mySum2())
add,sub,mul = mySum2()
print(add,sub,mul)













