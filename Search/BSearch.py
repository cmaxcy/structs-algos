'''
    Gets the index of the value 'key' in 'list' between the indexes 'low' and 'high'
    Repeated recursively until the midpoint is 'key', or the lower boundary is bigger than the upper
    A returned index of -1 indicates that 'key' was not found
'''
def get_index(low, high, list, key):

    # determines middle index
    mid_index = (low + high) / 2

    # if True 'key' cannot be in 'list'
    if low > high:
        return -1

    elif list[mid_index] == key:
        return mid_index

    # 'key' cannot lie in upper half
    elif list[mid_index] > key:
        return get_index(low, mid_index - 1, list, key)

    # 'key' cannot lie in lower half
    elif list[mid_index] < key:
        return get_index(mid_index + 1, high, list, key)
 
    # should be handled by above cases
    else:
        print "OTHER???"
        return -2

"""
    Uses get_index to return whether or not 'key' is in 'list'
"""
def check_for(key, list):

    # 'key' NOT in 'list'
    if get_index(0, len(list) - 1, list, key) is -1:
        return False

    # 'key' in list
    else:
        return True

'''
    Sample run
'''
def main():
    
    # numbrs to be searched through
    list = [1,1,2,3,5,8,13]

    # displays boolean value for whether or not 'i' is one of the numbers
    for i in range(50):
        print str(i) + ": " + str(check_for(i, list))
