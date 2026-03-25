require 'set'
# Accepts a hash of words to an array of translations.
# Returns a hash of words mapped to an array of all possible combinations of translations.
def possibilities(words)
  new_words = Hash.new
  words.each_key do |key|
    new_words[key] = generate_word_lists(words[key])
  end
  
  return new_words
end

def generate_word_lists(word_list)
  if word_list.size == 1
    return [word_list]
  end
  
  result = [word_list].to_set
  for i in 0...word_list.size
    result.merge(generate_word_lists([*word_list[0...i], *word_list[i+1...word_list.size]]))
  end
    
  return result.to_a.map{ |a| a.sort }.sort{ |a,b| a<=>b }
end