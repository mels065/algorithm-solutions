#!/bin/python3

import math
import os
import random
import re
import sys

#
# Complete the 'breakingRecords' function below.
#
# The function is expected to return an INTEGER_ARRAY.
# The function accepts INTEGER_ARRAY scores as parameter.
#

def breakingRecords(scores):
    maxScore = scores[0]
    minScore = scores[0]
    
    record = [0, 0]
    for i in range(1, len(scores)):
        if scores[i] > maxScore:
            record[0] += 1
            maxScore = scores[i]
        elif scores[i] < minScore:
            record[1] += 1
            minScore = scores[i]
    
    return record
        

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')

    n = int(input().strip())

    scores = list(map(int, input().rstrip().split()))

    result = breakingRecords(scores)

    fptr.write(' '.join(map(str, result)))
    fptr.write('\n')

    fptr.close()
