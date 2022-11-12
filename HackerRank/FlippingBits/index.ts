'use strict';

import { WriteStream, createWriteStream } from "fs";
process.stdin.resume();
process.stdin.setEncoding('utf-8');

let inputString: string = '';
let inputLines: string[] = [];
let currentLine: number = 0;

process.stdin.on('data', function(inputStdin: string): void {
    inputString += inputStdin;
});

process.stdin.on('end', function(): void {
    inputLines = inputString.split('\n');
    inputString = '';

    main();
});

function readLine(): string {
    return inputLines[currentLine++];
}

/*
 * Complete the 'flippingBits' function below.
 *
 * The function is expected to return a LONG_INTEGER.
 * The function accepts LONG_INTEGER n as parameter.
 */

function flippingBits(n: number): number {
    // Write your code here
    let remainder = n;
    let result = 0;
    for (let i = 31; i >= 0; i--) {
        if (remainder / 2**i < 1) {
            result += 2**i;
        } else {
            remainder -= 2**i;
        }
    }
    
    return result;
}

function main() {
    const ws: WriteStream = createWriteStream(process.env['OUTPUT_PATH']);

    const q: number = parseInt(readLine().trim(), 10);

    for (let qItr: number = 0; qItr < q; qItr++) {
        const n: number = parseInt(readLine().trim(), 10);

        const result: number = flippingBits(n);

        ws.write(result + '\n');
    }

    ws.end();
}
