Mini Crossword Generator
========================

This project attempts to generate a 5 by 5 miniature crossword (like you can find in the New York Times) given a grid with a couple words already in. It selects the word that will maximize the number of possibilities remaining (i.e. the least restrictive word) in order to come to one possible solution quickly. There is a GUI implemented that allows the user to enter words into a grid and see all of the permutations that are being tested until a complete puzzle is found. Even with our very expansive word list, it is fairly common to not find any completed crossword. 

You can now specify how much of the word list you would like to use. For example, if you only want words that are very common, you can restrict the wordlist to about 30% of the entire list, and you'll probably recognize everything there. There is a tradeoff here: the fewer words you use, the less likely you are to actually find a solution, but at least you won't have to google every single word (there are some absurdly obscure words in the master list).

The GUI has been updated with a handy menu that allows rapid functionality through keyboard shortcuts. 

Here's a grid based on "ski" and "slump":

![Sample Grid](https://github.com/bakitybacon/minicrossword/blob/master/fullgrid.png)

Here's an empty grid, ready for export after a few clues are added:

![Sample Grid](https://github.com/bakitybacon/minicrossword/blob/master/grid.png)

The algorithm uses hashing to increase execution speed significantly. This way, the algorithm doesn't have to regenerate the same possibilities three hundred times for the same letter pattern. 

It now supports black squares and can solve puzzles provided that each word is at least three letters long, which is a convention for a mini crossword. there's not a whole lot one can do with two letters, after all.

Word list: almost nine thousand words in 5word.game, or a little over six thousand in 5wordspell.game, which only contains the words in 5word.game that are recognized by spell check. This list is extremely expansive and contains a lot of words nobody has ever heard of. It does, however, tend to get an answer more than just, say, the top 500 words. 

Future Plans:

* if a user puts an unfamiliar word in the crossword, the algorithm should add that word to the list temporarily so that the algorithm can terminate. Otherwise, it would consider that word invalid.
* the three letter minimum for words in mini crosswords should be enforced by the GUI.
* the GUI should be updated to be more intuitive, perhaps with some buttons that could control searching.
* the GUI should support searching beyond the first possibility found.
* the GUI should probably not update so frequently that it cannot even fully redraw the screen before being asked to update again.
* allow the user to export to PDF, Word, maybe some crossword formats, if they exist. That's what this program is for, after all.
* generalize the concept of a crossword to higher (and lower) dimensions just for the fun of it (because who doesn't want a 3D crossword, right?)
