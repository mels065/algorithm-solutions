'use strict';

process.stdin.resume();
process.stdin.setEncoding('utf-8');

let inputString = '';
let currentLine = 0;

process.stdin.on('data', function(inputStdin) {
    inputString += inputStdin;
});

process.stdin.on('end', function() {
    inputString = inputString.split('\n');

    main();
});

function readLine() {
    return inputString[currentLine++];
}

/*
 * Complete the 'miniMaxSum' function below.
 *
 * The function accepts INTEGER_ARRAY arr as parameter.
 */

function miniMaxSum(arr) {
    const sortedArr = arr.sort((a, b) => a - b);
    
    const miniSum = sortedArr.slice(0, 4).reduce(sum, 0);
    const maxSum = sortedArr.slice(1, 5).reduce(sum, 0);
    
    console.log(miniSum, maxSum);
}

function sum(a, b) {
    return a + b;
}

function main() {

    const arr = readLine().replace(/\s+$/g, '').split(' ').map(arrTemp => parseInt(arrTemp, 10));

    miniMaxSum(arr);
}
