Mini Crossword Generator
========================

This project attempts to generate a 5 by 5 miniature crossword (like you can find in the New York Times) given a grid with a couple words already in. It selects the word that will maximize the number of possibilites remaining (i.e. the least restrictive word) in order to come to one possible solution quickly. There is a GUI implemented that allows the user to enter a word and see all of the permutations that have to be tested. As of right now, even with our very expansive word list, it is common to not find any completed crossword. 

Word list: almost nine thousand words in 5word.game, or a little over six thousand in 5wordspell.game, which only contains the words in 5word.game that are recognized by spell check. This list is extremely expansive and contains a lot of words nobody has ever heard of. It does, however, tend to get an answer more than just, say, the top 500 words. 

Future Plans:

* allow black squares in the searching algorithm
* generalize the concept of a crossword to higher (and lower) dimensions just for the fun of it (because who doesn't want a 3D crossword, right?)
* implement hashing so the program doesn't have to regenerate the same possibilities three hundred times for the same letter pattern. So, if we have c**** as a pattern, possibilites include chant, count, etc... Once we have our answer, there is no reason to re-run the generation function.
