
import turtle
import os
a = 0
b = 0

turtle.bgcolor("Black")
turtle.speed(50)
turtle.pencolor("Blue")
turtle.penup()
turtle.goto(0, 200)
turtle.pendown()

while True:
	turtle.forward(a)
	turtle.right(b)
	a+=3
	b+=1
	if b == 200:
		break
	
os.system("pause")

