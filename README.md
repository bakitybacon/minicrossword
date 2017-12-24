Mini Crossword Generator
========================

This project attempts to generate a 5 by 5 miniature crossword (like you can find in the New York Times) given a grid with a couple words already in. It selects the word that will maximize the number of possibilites remaining (i.e. the least restrictive word) in order to come to one possible solution quickly. There is a GUI implemented that allows the user to enter a word and see all of the permutations that have to be tested. As of right now, even with our very expansive word list, it is common to not find any completed crossword. 

The algorithm uses hashing to increase execution speed significantly. This way, program doesn't have to regenerate the same possibilities three hundred times for the same letter pattern. 

It now supports black squares and can solve puzzles provided that each word is at least three letters long, which is a convention for a mini crossword. there's not a whole lot one can do with two letters, after all.

Word list: almost nine thousand words in 5word.game, or a little over six thousand in 5wordspell.game, which only contains the words in 5word.game that are recognized by spell check. This list is extremely expansive and contains a lot of words nobody has ever heard of. It does, however, tend to get an answer more than just, say, the top 500 words. 

Future Plans:

* if a user puts an unfamiliar word in the crossword, the algorithm should add that word to the list temporarily so that the algorithm can terminate. otherwise, it would consider that word invalid.
* the three letter minimum for words in mini crosswords should be enforced by the GUI.
* the GUI should be updated to be more intuitive, perhaps with some buttons that could control searching.
* the GUI should support searching beyond the first possibility found.
* the wordlists should be sorted in order of usage with the most frequently used first so that a user can say "I only want words that are actually used more than 'zyzzyvas'" by only reading say the top 50% of each list
* generalize the concept of a crossword to higher (and lower) dimensions just for the fun of it (because who doesn't want a 3D crossword, right?)
