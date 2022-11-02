import fileinput

# Enter your code here. Read input from STDIN. Print output to STDOUT
def camel_case():
    for f in fileinput.input():
        [operation, type_of_string, string] = f.split(';')
        if string[-1] == '\n':
            string = string[0:-1]

        if operation == 'S':
            if type_of_string == 'M':
                print(split_into_words(string)[0:-2])
            elif type_of_string == 'C' or type_of_string == 'V':
                print(split_into_words(string))
        elif operation == 'C':
            if type_of_string == 'M':
                print("{}()".format(convert_to_camel(string).split(' ')[0]))
            elif type_of_string == 'C':
                print(convert_to_camel(string, True))
            elif type_of_string == 'V':
                print(convert_to_camel(string))
                
def split_into_words(string):
    new_string = string[0].lower()
    
    for i in range(1, len(string)):
        if string[i] >= 'A' and string[i] <= 'Z':
            new_string += " {}".format(string[i].lower())
        else:
            new_string += string[i]
    
    return new_string.strip()

def convert_to_camel(string, is_class=False):
    new_string = string[0].upper() if is_class else string[0].lower()
    
    capitalize_next = False
    for i in range(1, len(string)):
        if string[i] == ' ':
            capitalize_next = True
        elif capitalize_next and string[i].isalpha():
            new_string += string[i].upper()
            capitalize_next = False
        else:
            new_string += string[i].lower()
    
    return new_string.strip()
    
camel_case()
