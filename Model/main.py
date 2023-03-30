import os

# This is a sample Python script.

# Press Mayús+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print_hi('PyCharm')
    mylist = []
    print(mylist)
    for i in range(0,10):
        mylist.append(i)
        if len(mylist) > 3:
            mylist.pop(0)
        print(mylist)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
