# [Life of Possibilities](https://www.codewars.com/kata/514a17e911ea4fb54200005a/train/ruby)

Given a map of words and their translations, generate a list of all possible unique combinations of translations, sorted lexically.

For example, given the map of words:

```ruby
words = {
  life:   ['vida', 'vie', 'Leben'],
  death:  ['muerte', 'mort', 'Tode'] }
}
```

The method should return the result:

```ruby
 {
  life: [
    ['Leben'],
    ['Leben', 'vida'],
    ['Leben', 'vida', 'vie'],
    ['Leben', 'vie'],
    ['vida'],
    ['vida', 'vie'],
    ['vie']
  ],
  death: [
    ['Tode'],
    ['Tode', 'mort'],
    ['Tode', 'mort', 'muerte'],
    ['Tode', 'muerte'],
    ['mort'],
    ['mort', 'muerte'],
    ['muerte']
  ]
}
```
