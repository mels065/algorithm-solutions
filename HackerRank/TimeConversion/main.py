#!/bin/python3

import math
import os
import random
import re
import sys

#
# Complete the 'timeConversion' function below.
#
# The function is expected to return a STRING.
# The function accepts STRING s as parameter.
#

def timeConversion(s):
    # Write your code here
    [hour, minutes, secondsAndMerideum] = s.split(':')
    seconds = secondsAndMerideum[0:2]
    merideum = secondsAndMerideum[2:]
    
    if (int(hour) == 12 and merideum == 'AM'):
        hour = "00"
    elif (merideum == 'PM' and int(hour) < 12):
        hour = str(12 + int(hour))
        
    return "{}:{}:{}".format(hour, minutes, seconds)

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')

    s = input()

    result = timeConversion(s)

    fptr.write(result + '\n')

    fptr.close()
