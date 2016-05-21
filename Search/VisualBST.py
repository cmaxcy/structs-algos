import turtle
import math


class Node:
    def __init__(self, value=0, left_child=None, right_child=None, parent=None, depth=0):
        self.value = value
        self.left_child = left_child
        self.right_child = right_child
        self.parent = parent
        self.depth = depth

    # returns True if the node has no children
    def is_leaf(self):
        return (self.left_child is None) and (self.right_child is None)


class BTree:
    def __init__(self, root=None):
        self.root = root

    # returns whether or not the tree has any nodes in it
    def is_empty(self):
        return self.root is None

    # prints out the values of the nodes in the tree in ascending order
    def help_ordered(self, node):

        # if the node's left child exists
        if node.left_child is not None:
            self.help_ordered(node.left_child)

        print(node.value)

        # if the node's right child exists
        if node.right_child is not None:
            self.help_ordered(node.right_child)

    # inserts a node into its correct position
    # returns whether or not the value was added
    def help_insert(self, value, node):

        # if null node is passed, end function
        if node is None:
            return

        # if the value of the new node is less than the current node
        if value < node.value:

            # if the current node has no left child
            if node.left_child is None:

                # assign left child to be new node, with parent being current node
                node.left_child = Node(value, parent=node, depth=(node.depth+1))
                return True

            # if the current node has a left child, repeat with left child being the current
            return self.help_insert(value, node.left_child)

        # if the value of the new nod is greater than the current node
        elif value > node.value:

            # if the current node has no right child
            if node.right_child is None:

                # assign right child to be new node, with parent being current node
                node.right_child = Node(value, parent=node, depth=(node.depth+1))
                return True

            # if the current node has a right child, repeat with right child being the current
            return self.help_insert(value, node.right_child)

        # if the new node already exists in the tree
        else:
            return False

    # searches for a value in the tree
    def help_find(self, node, value):

        # if null node is passed, end function
        if node is None:
            return False

        # if the value of the current node matches the value passed
        if value is node.value:
            return True

        # if the value passed is less than the current node's value
        elif value < node.value:

            # if the current node has NO left child, return False
            if node.left_child is None:
                return False

            # if the current node has a left child, repeat with left child taking the place of the current node
            return self.help_find(node.left_child, value)

        # if the value passed is greater than the current node's value
        elif value > node.value:

            # if the current node has NO right child
            if node.right_child is None:
                return False

            # if the current node has a right child, repeat with right child taking the place of the current node
            return self.help_find(node.right_child, value)

    # draws the node and the paths leading to the child nodes
    def help_illustrate(self, node, the_turtle):

        # used to draw the left child node
        left_turt = the_turtle.clone()
        left_turt.color("black")
        left_turt.setheading(200 + (node.depth * 10))

        # used to draw the right child node
        right_turt = the_turtle.clone()
        right_turt.color("black")
        right_turt.setheading(340 - (node.depth * 10))

        # saved for the draw_num function
        current_x = the_turtle.xcor()
        current_y = the_turtle.ycor()

        # draw the node
        self.draw_num(node.value, current_x, current_y, self.color)

        # if the left child of the node exists
        if node.left_child is not None:

            # draw the path to the  node
            left_turt.pu()
            left_turt.fd(20)
            left_turt.pd()
            left_turt.fd(300/(math.sqrt(2 * node.depth + 1)))

            # recursively apply function to the left child
            self.help_illustrate(node.left_child, left_turt)

        # if the right child of the node exists
        if node.right_child is not None:

            # draw the path to the node
            right_turt.pu()
            right_turt.fd(10)
            right_turt.pd()
            right_turt.fd(300/(math.sqrt(2 * node.depth + 1)))

            # recursively apply the function to the right child
            self.help_illustrate(node.right_child, right_turt)

    # uses help_insert to recursively place a new node where it belongs
    # returns whether or not the value was able to be added
    def insert(self, value):

        # if the tree is empty, the new node becomes the root
        if self.is_empty():
            self.root = Node(value)
            return

        # else starting at the root, recursively place the node
        return self.help_insert(value, self.root)

    # uses help_find to recursively search for a value
    def find(self, value):

        # an empty tree is guaranteed to not contain the value
        if self.is_empty():
            return False

        # else starting at the root, recursively find the node
        return self.help_find(self.root, value)

    # uses help_ordered to recursively print out the values of the nodes in the tree
    def ordered(self):

        # prevents a null reference to the root if it is not there
        if self.is_empty():
            print("Empty")
            return

        self.help_ordered(self.root)
        return

    # uses help_illustrate to draw out a pre-order traversal of the tree
    def illustrate(self, color="red"):

        # if the tree is empty, exit program
        if self.is_empty():
            print("Empty")
            return

        # set turtle settings
        turtle.ht()
        turtle.pu()
        turtle.speed(0)

        # move turtle to near top of string
        turtle.goto(0, 250)

        # set color of the numbers on the nodes
        self.color = color

        # begin drawing the tree
        self.help_illustrate(self.root, turtle)

    # draws a filled circle with the number passed in the middle
    def draw_num(self, num, x_pos, y_pos, color="red"):

        # define drawing settings
        my_drawer = turtle.clone()
        my_drawer.ht()
        my_drawer.pu()
        my_drawer.speed(0)
        my_drawer.goto(x_pos, y_pos)

        # save number length
        num_len = len(str(num))

        # creates black dot to encircle number
        my_drawer.color("black")
        my_drawer.dot(50)

        # update font color and size, ensures writing is centered
        my_drawer.color(color)
        my_drawer.right(90)
        my_drawer.fd(10)
        my_drawer.right(90)
        my_drawer.fd(4 * num_len)

        # write the number passed inside of the black dot
        my_drawer.write(num, font=("arial", 12, "bold"))


def main():
    the_tree = BTree()

    the_tree.insert(8)
    the_tree.insert(90)
    the_tree.insert(1)
    the_tree.insert(900)

    the_tree.illustrate("yellow")
