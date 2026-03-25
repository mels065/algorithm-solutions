function seqToOne(n) {
  if (n === 0) return [0, 1];

  const change = -(n / Math.abs(n));
  const result = [];
  console.log(change);

  for (let i = n; i !== 1; i += change) {
    result.push(i);
  }

  return [...result, 1];
}
