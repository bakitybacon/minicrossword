/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicrossword;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author ciarwen
 */
public class MiniCrossword {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
       // TODO code application logic here
        
        // Checks crossword word entry and direction of indices.
        /*Crossword c = new Crossword(5, 6);
        System.out.println(c);
        c.setLetter(2, 1, 'x');
        System.out.println(c);
        c.setWordRow(3, "spains");
        System.out.println(c);
        c.setWordCol(4, "whine");
        System.out.println(c);
        System.out.println(c.isFinished());
        Crossword d = new Crossword(5, 5);
        for(int i = 0; i < 5; i++)
        {
            System.out.println();
            d.setWordRow(i, "spain");
            System.out.println(d);
            System.out.println(d.isFinished());
        }
        // Checking for how boolean array is initialized
        boolean[] b = new boolean[5];
        for(int i = 0; i < b.length; i++)
        {
            System.out.print(b[i]);
        }
        System.out.println();
        // Checking Word for setting and accessing the parameters
        Word x = new Word("eagle");
        Word y = new Word("pain");
        Word z = new Word("forgo");
        x.setNumPoss(2);
        y.setNumPoss(1);
        z.setNumPoss(0);
        // Checking Array Sort command for Word array
        Word[] stuff = new Word[3];
        stuff[0] = x;
        stuff[1] = y;
        stuff[2] = z;
        Arrays.sort(stuff, new WordSortbyNumPoss());
        for(int i = 0; i < stuff.length; i++)
        {
            System.out.print(stuff[i] + " ");
        }
        System.out.println();
        // Check functions pertaining to generating a list of possible answers
        Crossword example = new Crossword(5, 5);
        System.out.println(example);
        example.setWordCol(0, "ghana");
        System.out.println(example);
        for(int j = 0; j < 5; j++)
        {
            String[] poss = example.getPossRow(j);
            for(int i = 0; i < poss.length; i++)
            {
                System.out.print(poss[i] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(example.selectIndexRow());
        System.out.println(example.IndexRow());
        Crossword example2 = new Crossword(5, 5);
        System.out.println(example2);
        example2.setWordRow(0, "grace");
        System.out.println(example2);
        for(int j = 0; j < 5; j++)
        {
            String[] poss2 = example2.getPossCol(j);
            for(int i = 0; i < poss2.length; i++)
            {
                System.out.print(poss2[i] + " ");
            }
            System.out.println();
        }
        System.out.println(example2.selectIndexCol());
        System.out.println(example2.IndexRow());
        // Checking functionality of the undo method, whether function can
        // successfully undo moves in its history.
        Crossword undo = new Crossword(5, 5);
        System.out.println(undo);
        System.out.println(undo.sumPoss());
        undo.setWordRow(0, "ghana");
        System.out.println(undo);
        System.out.println(undo.sumPoss());
        undo.setWordCol(1, "helix");
        System.out.println(undo);
        System.out.println(undo.sumPoss());
        undo.undoLast();
        System.out.println(undo);
        System.out.println(undo.sumPoss());
        // Checking Searcher class, which only exists to run the Search command
        // Checks base cases (c and d), and the recursive case with an empty grid.
        Searcher pl = new Searcher();
        /*System.out.println(pl.Search(c));
        System.out.println();
        System.out.println(pl.Search(d));
        System.out.println();
        System.out.println(pl.Search(new Crossword(5, 5)));*/
        // Used to test how objects are inserted and removed in an ArrayList
        // Functionally, I need a for of a stack, last in first out object.
        /*ArrayList<Integer> list = new ArrayList<Integer>();
        int i = 1;
        int j = 4;
        System.out.println(list);
        list.add(i);
        System.out.println(list);
        list.add(0, j);
        System.out.println(list);
        list.add(0, 10);
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
        list.remove (0);
        System.out.println(list);
        Crossword edit = new Crossword(5, 5);
        System.out.println(edit);
        edit.setWordRow(0, "chain");
        System.out.println(edit);
        edit.setWordCol(0, "count");
        System.out.println(edit);
        edit.undoLast();
        System.out.println(edit);
        edit.undoLast();
        System.out.println(edit);
        String[] wordlist = new String[10];
        wordlist[0] = "grace";
        wordlist[1] = "helix";
        wordlist[2] = "abide";
        wordlist[3] = "never";
        wordlist[4] = "alert";
        wordlist[5] = "ghana";
        wordlist[6] = "rebel";
        wordlist[7] = "alive";
        wordlist[8] = "cider";
        wordlist[9] = "exert";
        Crossword insert = new Crossword(5, 5, wordlist);
        //System.out.println(pl.Search(insert));
        Crossword insert2 = new Crossword(5, 5, wordlist);
        insert2.setWordCol(0, "grace");
        insert2.setWordCol(1, "helix");
        insert2.setWordCol(2, "abide");
        insert2.setWordCol(3, "never");
        insert2.setWordRow(1, "rebel");
        insert2.setWordRow(3, "cider");
        System.out.println("Initial Insert2\n" + insert2);
        Crossword insert3 = new Crossword(5, 5, wordlist);
        insert3.setWordRow(0, "grace");
        insert3.setWordRow(1, "helix");
        insert3.setWordRow(2, "abide");
        insert3.setWordRow(3, "never");
        insert3.setWordCol(1, "rebel");
        insert3.setWordCol(3, "cider");
        System.out.println("Initial Insert3\n" + insert3);
        System.out.println("Searching insert2\n"+pl.SearchAll(insert2));
        System.out.println("Searching insert3\n" +pl.SearchAll(insert3));
        String[] wordsy = new String[505];
        wordsy[0] = "about";
        wordsy[1] = "above";
        wordsy[2] = "abuse";
        wordsy[3] = "actor";
        wordsy[4] = "acute";
        wordsy[5] = "admit";
        wordsy[6] = "adopt";
        wordsy[7] = "adult";
        wordsy[8] = "after";
        wordsy[9] = "again";
        wordsy[10] = "agent";
        wordsy[11] = "agree";
        wordsy[12] = "ahead";
        wordsy[13] = "alarm";
        wordsy[14] = "album";
        wordsy[15] = "alert";
        wordsy[16] = "alike";
        wordsy[17] = "alive";
        wordsy[18] = "allow";
        wordsy[19] = "alone";
        wordsy[20] = "along";
        wordsy[21] = "alter";
        wordsy[22] = "among";
        wordsy[23] = "anger";
        wordsy[24] = "angle";
        wordsy[25] = "angry";
        wordsy[26] = "apart";
        wordsy[27] = "apple";
        wordsy[28] = "apply";
        wordsy[29] = "arena";
        wordsy[30] = "argue";
        wordsy[31] = "arise";
        wordsy[32] = "array";
        wordsy[33] = "aside";
        wordsy[34] = "asset";
        wordsy[35] = "audio";
        wordsy[36] = "audit";
        wordsy[37] = "avoid";
        wordsy[38] = "award";
        wordsy[39] = "aware";
        wordsy[40] = "badly";
        wordsy[41] = "baker";
        wordsy[42] = "bases";
        wordsy[43] = "basic";
        wordsy[44] = "basis";
        wordsy[45] = "beach";
        wordsy[46] = "began";
        wordsy[47] = "begin";
        wordsy[48] = "being";
        wordsy[49] = "below";
        wordsy[50] = "bench";
        wordsy[51] = "billy";
        wordsy[52] = "birth";
        wordsy[53] = "black";
        wordsy[54] = "blame";
        wordsy[55] = "blind";
        wordsy[56] = "block";
        wordsy[57] = "blood";
        wordsy[58] = "board";
        wordsy[59] = "boost";
        wordsy[60] = "booth";
        wordsy[61] = "bound";
        wordsy[62] = "brain";
        wordsy[63] = "brand";
        wordsy[64] = "bread";
        wordsy[65] = "break";
        wordsy[66] = "breed";
        wordsy[67] = "brief";
        wordsy[68] = "bring";
        wordsy[69] = "broad";
        wordsy[70] = "broke";
        wordsy[71] = "brown";
        wordsy[72] = "build";
        wordsy[73] = "built";
        wordsy[74] = "buyer";
        wordsy[75] = "cable";
        wordsy[76] = "calif";
        wordsy[77] = "carry";
        wordsy[78] = "catch";
        wordsy[79] = "cause";
        wordsy[80] = "chain";
        wordsy[81] = "chair";
        wordsy[82] = "chart";
        wordsy[83] = "chase";
        wordsy[84] = "cheap";
        wordsy[85] = "check";
        wordsy[86] = "chest";
        wordsy[87] = "chief";
        wordsy[88] = "child";
        wordsy[89] = "china";
        wordsy[90] = "chose";
        wordsy[91] = "civil";
        wordsy[92] = "claim";
        wordsy[93] = "class";
        wordsy[94] = "clean";
        wordsy[95] = "clear";
        wordsy[96] = "click";
        wordsy[97] = "clock";
        wordsy[98] = "close";
        wordsy[99] = "coach";
        wordsy[100] = "coast";
        wordsy[101] = "could";
        wordsy[102] = "count";
        wordsy[103] = "court";
        wordsy[104] = "cover";
        wordsy[105] = "craft";
        wordsy[106] = "crash";
        wordsy[107] = "cream";
        wordsy[108] = "crime";
        wordsy[109] = "crowd";
        wordsy[110] = "crown";
        wordsy[111] = "curve";
        wordsy[112] = "cycle";
        wordsy[113] = "daily";
        wordsy[114] = "dance";
        wordsy[115] = "dated";
        wordsy[116] = "dealt";
        wordsy[117] = "death";
        wordsy[118] = "debut";
        wordsy[119] = "delay";
        wordsy[120] = "depth";
        wordsy[121] = "doing";
        wordsy[122] = "doubt";
        wordsy[123] = "dozen";
        wordsy[124] = "draft";
        wordsy[125] = "drama";
        wordsy[126] = "drawn";
        wordsy[127] = "dream";
        wordsy[128] = "dress";
        wordsy[129] = "drill";
        wordsy[130] = "drink";
        wordsy[131] = "drive";
        wordsy[132] = "drove";
        wordsy[133] = "dying";
        wordsy[134] = "eager";
        wordsy[135] = "early";
        wordsy[136] = "earth";
        wordsy[137] = "eight";
        wordsy[138] = "elite";
        wordsy[139] = "empty";
        wordsy[140] = "enemy";
        wordsy[141] = "enjoy";
        wordsy[142] = "enter";
        wordsy[143] = "entry";
        wordsy[144] = "equal";
        wordsy[145] = "error";
        wordsy[146] = "event";
        wordsy[147] = "every";
        wordsy[148] = "exact";
        wordsy[149] = "exist";
        wordsy[150] = "extra";
        wordsy[151] = "faith";
        wordsy[152] = "false";
        wordsy[153] = "fault";
        wordsy[154] = "fiber";
        wordsy[155] = "field";
        wordsy[156] = "fifth";
        wordsy[157] = "fifty";
        wordsy[158] = "fight";
        wordsy[159] = "final";
        wordsy[160] = "first";
        wordsy[161] = "fixed";
        wordsy[162] = "flash";
        wordsy[163] = "fleet";
        wordsy[164] = "floor";
        wordsy[165] = "fluid";
        wordsy[166] = "focus";
        wordsy[167] = "force";
        wordsy[168] = "forth";
        wordsy[169] = "forty";
        wordsy[170] = "forum";
        wordsy[171] = "found";
        wordsy[172] = "frame";
        wordsy[173] = "frank";
        wordsy[174] = "fraud";
        wordsy[175] = "fresh";
        wordsy[176] = "front";
        wordsy[177] = "fruit";
        wordsy[178] = "fully";
        wordsy[179] = "funny";
        wordsy[180] = "giant";
        wordsy[181] = "given";
        wordsy[182] = "glass";
        wordsy[183] = "globe";
        wordsy[184] = "going";
        wordsy[185] = "grace";
        wordsy[186] = "grade";
        wordsy[187] = "grand";
        wordsy[188] = "grant";
        wordsy[189] = "grass";
        wordsy[190] = "great";
        wordsy[191] = "green";
        wordsy[192] = "gross";
        wordsy[193] = "group";
        wordsy[194] = "grown";
        wordsy[195] = "guard";
        wordsy[196] = "guess";
        wordsy[197] = "guest";
        wordsy[198] = "guide";
        wordsy[199] = "happy";
        wordsy[200] = "harry";
        wordsy[201] = "heart";
        wordsy[202] = "heavy";
        wordsy[203] = "hence";
        wordsy[204] = "henry";
        wordsy[205] = "horse";
        wordsy[206] = "hotel";
        wordsy[207] = "house";
        wordsy[208] = "human";
        wordsy[209] = "ideal";
        wordsy[210] = "image";
        wordsy[211] = "index";
        wordsy[212] = "inner";
        wordsy[213] = "input";
        wordsy[214] = "issue";
        wordsy[215] = "japan";
        wordsy[216] = "jimmy";
        wordsy[217] = "joint";
        wordsy[218] = "jones";
        wordsy[219] = "judge";
        wordsy[220] = "known";
        wordsy[221] = "label";
        wordsy[222] = "large";
        wordsy[223] = "laser";
        wordsy[224] = "later";
        wordsy[225] = "laugh";
        wordsy[226] = "layer";
        wordsy[227] = "learn";
        wordsy[228] = "lease";
        wordsy[229] = "least";
        wordsy[230] = "leave";
        wordsy[231] = "legal";
        wordsy[232] = "level";
        wordsy[233] = "lewis";
        wordsy[234] = "light";
        wordsy[235] = "limit";
        wordsy[236] = "links";
        wordsy[237] = "lives";
        wordsy[238] = "local";
        wordsy[239] = "logic";
        wordsy[240] = "loose";
        wordsy[241] = "lower";
        wordsy[242] = "lucky";
        wordsy[243] = "lunch";
        wordsy[244] = "lying";
        wordsy[245] = "magic";
        wordsy[246] = "major";
        wordsy[247] = "maker";
        wordsy[248] = "march";
        wordsy[249] = "maria";
        wordsy[250] = "match";
        wordsy[251] = "maybe";
        wordsy[252] = "mayor";
        wordsy[253] = "meant";
        wordsy[254] = "media";
        wordsy[255] = "metal";
        wordsy[256] = "might";
        wordsy[257] = "minor";
        wordsy[258] = "minus";
        wordsy[259] = "mixed";
        wordsy[260] = "model";
        wordsy[261] = "money";
        wordsy[262] = "month";
        wordsy[263] = "moral";
        wordsy[264] = "motor";
        wordsy[265] = "mount";
        wordsy[266] = "mouse";
        wordsy[267] = "mouth";
        wordsy[268] = "movie";
        wordsy[269] = "music";
        wordsy[270] = "needs";
        wordsy[271] = "never";
        wordsy[272] = "newly";
        wordsy[273] = "night";
        wordsy[274] = "noise";
        wordsy[275] = "north";
        wordsy[276] = "noted";
        wordsy[277] = "novel";
        wordsy[278] = "nurse";
        wordsy[279] = "occur";
        wordsy[280] = "ocean";
        wordsy[281] = "offer";
        wordsy[282] = "often";
        wordsy[283] = "order";
        wordsy[284] = "other";
        wordsy[285] = "ought";
        wordsy[286] = "paint";
        wordsy[287] = "panel";
        wordsy[288] = "paper";
        wordsy[289] = "party";
        wordsy[290] = "peace";
        wordsy[291] = "peter";
        wordsy[292] = "phase";
        wordsy[293] = "phone";
        wordsy[294] = "photo";
        wordsy[295] = "piece";
        wordsy[296] = "pilot";
        wordsy[297] = "pitch";
        wordsy[298] = "place";
        wordsy[299] = "plain";
        wordsy[300] = "plane";
        wordsy[301] = "plant";
        wordsy[302] = "plate";
        wordsy[303] = "point";
        wordsy[304] = "pound";
        wordsy[305] = "power";
        wordsy[306] = "press";
        wordsy[307] = "price";
        wordsy[308] = "pride";
        wordsy[309] = "prime";
        wordsy[310] = "print";
        wordsy[311] = "prior";
        wordsy[312] = "prize";
        wordsy[313] = "proof";
        wordsy[314] = "proud";
        wordsy[315] = "prove";
        wordsy[316] = "queen";
        wordsy[317] = "quick";
        wordsy[318] = "quiet";
        wordsy[319] = "quite";
        wordsy[320] = "radio";
        wordsy[321] = "raise";
        wordsy[322] = "range";
        wordsy[323] = "rapid";
        wordsy[324] = "ratio";
        wordsy[325] = "reach";
        wordsy[326] = "ready";
        wordsy[327] = "refer";
        wordsy[328] = "right";
        wordsy[329] = "rival";
        wordsy[330] = "river";
        wordsy[331] = "robin";
        wordsy[332] = "roger";
        wordsy[333] = "roman";
        wordsy[334] = "rough";
        wordsy[335] = "round";
        wordsy[336] = "route";
        wordsy[337] = "royal";
        wordsy[338] = "rural";
        wordsy[339] = "scale";
        wordsy[340] = "scene";
        wordsy[341] = "scope";
        wordsy[342] = "score";
        wordsy[343] = "sense";
        wordsy[344] = "serve";
        wordsy[345] = "seven";
        wordsy[346] = "shall";
        wordsy[347] = "shape";
        wordsy[348] = "share";
        wordsy[349] = "sharp";
        wordsy[350] = "sheet";
        wordsy[351] = "shelf";
        wordsy[352] = "shell";
        wordsy[353] = "shift";
        wordsy[354] = "shirt";
        wordsy[355] = "shock";
        wordsy[356] = "shoot";
        wordsy[357] = "short";
        wordsy[358] = "shown";
        wordsy[359] = "sight";
        wordsy[360] = "since";
        wordsy[361] = "sixth";
        wordsy[362] = "sixty";
        wordsy[363] = "sized";
        wordsy[364] = "skill";
        wordsy[365] = "sleep";
        wordsy[366] = "slide";
        wordsy[367] = "small";
        wordsy[368] = "smart";
        wordsy[369] = "smile";
        wordsy[370] = "smith";
        wordsy[371] = "smoke";
        wordsy[372] = "solid";
        wordsy[373] = "solve";
        wordsy[374] = "sorry";
        wordsy[375] = "sound";
        wordsy[376] = "south";
        wordsy[377] = "space";
        wordsy[378] = "spare";
        wordsy[379] = "speak";
        wordsy[380] = "speed";
        wordsy[381] = "spend";
        wordsy[382] = "spent";
        wordsy[383] = "split";
        wordsy[384] = "spoke";
        wordsy[385] = "sport";
        wordsy[386] = "staff";
        wordsy[387] = "stage";
        wordsy[388] = "stake";
        wordsy[389] = "stand";
        wordsy[390] = "start";
        wordsy[391] = "state";
        wordsy[392] = "steam";
        wordsy[393] = "steel";
        wordsy[394] = "stick";
        wordsy[395] = "still";
        wordsy[396] = "stock";
        wordsy[397] = "stone";
        wordsy[398] = "stood";
        wordsy[399] = "store";
        wordsy[400] = "storm";
        wordsy[401] = "story";
        wordsy[402] = "strip";
        wordsy[403] = "stuck";
        wordsy[404] = "study";
        wordsy[405] = "stuff";
        wordsy[406] = "style";
        wordsy[407] = "sugar";
        wordsy[408] = "suite";
        wordsy[409] = "super";
        wordsy[410] = "sweet";
        wordsy[411] = "table";
        wordsy[412] = "taken";
        wordsy[413] = "taste";
        wordsy[414] = "taxes";
        wordsy[415] = "teach";
        wordsy[416] = "teeth";
        wordsy[417] = "terry";
        wordsy[418] = "texas";
        wordsy[419] = "thank";
        wordsy[420] = "theft";
        wordsy[421] = "their";
        wordsy[422] = "theme";
        wordsy[423] = "there";
        wordsy[424] = "these";
        wordsy[425] = "thick";
        wordsy[426] = "thing";
        wordsy[427] = "think";
        wordsy[428] = "third";
        wordsy[429] = "those";
        wordsy[430] = "three";
        wordsy[431] = "threw";
        wordsy[432] = "throw";
        wordsy[433] = "tight";
        wordsy[434] = "times";
        wordsy[435] = "tired";
        wordsy[436] = "title";
        wordsy[437] = "today";
        wordsy[438] = "topic";
        wordsy[439] = "total";
        wordsy[440] = "touch";
        wordsy[441] = "tough";
        wordsy[442] = "tower";
        wordsy[443] = "track";
        wordsy[444] = "trade";
        wordsy[445] = "train";
        wordsy[446] = "treat";
        wordsy[447] = "trend";
        wordsy[448] = "trial";
        wordsy[449] = "tried";
        wordsy[450] = "tries";
        wordsy[451] = "truck";
        wordsy[452] = "truly";
        wordsy[453] = "trust";
        wordsy[454] = "truth";
        wordsy[455] = "twice";
        wordsy[456] = "under";
        wordsy[457] = "undue";
        wordsy[458] = "union";
        wordsy[459] = "unity";
        wordsy[460] = "until";
        wordsy[461] = "upper";
        wordsy[462] = "upset";
        wordsy[463] = "urban";
        wordsy[464] = "usage";
        wordsy[465] = "usual";
        wordsy[466] = "valid";
        wordsy[467] = "value";
        wordsy[468] = "video";
        wordsy[469] = "virus";
        wordsy[470] = "visit";
        wordsy[471] = "vital";
        wordsy[472] = "voice";
        wordsy[473] = "waste";
        wordsy[474] = "watch";
        wordsy[475] = "water";
        wordsy[476] = "wheel";
        wordsy[477] = "where";
        wordsy[478] = "which";
        wordsy[479] = "while";
        wordsy[480] = "white";
        wordsy[481] = "whole";
        wordsy[482] = "whose";
        wordsy[483] = "woman";
        wordsy[484] = "women";
        wordsy[485] = "world";
        wordsy[486] = "worry";
        wordsy[487] = "worse";
        wordsy[488] = "worst";
        wordsy[489] = "worth";
        wordsy[490] = "would";
        wordsy[491] = "wound";
        wordsy[492] = "write";
        wordsy[493] = "wrong";
        wordsy[494] = "wrote";
        wordsy[495] = "yield";
        wordsy[496] = "young";
        wordsy[497] = "youth";
        wordsy[498] = "zebra";
        wordsy[499] = "zumba";
        wordsy[500] = "erudo";
        wordsy[501] = "reble";
        wordsy[502] = "edile";
        wordsy[503] = "deart";
        wordsy[504] = "traed";*/
        /*Crossword full = new Crossword(5, 5, wordsy);
        System.out.println("With Wordsy");
        System.out.println(pl.Search(full));*/
        try
        {
            Searcher pl = new Searcher();
            Scanner CrosswordClues = new Scanner(new File("5word.game"));
            int numWords = CrosswordClues.nextInt();
            String[] wordy = new String[numWords];
            for(int i = 0; i < numWords; i++)
            {
                wordy[i] = CrosswordClues.next();
            }
            CrosswordClues.close();
            System.out.println("Success");
            /*for(int i = 0; i < numWords; i++)
            {
                System.out.println(wordy[i]);
            }*/
            
            //All of the words list used are taken from poslarchive.com.
            //2 letter words have a list of 100 words
            //3 letter words are around 1000,
            //4 letter words over 4000
            //and 5 letter words nearly 9000;
            //Yes, yes, but it's not over 9000, so shut up.
            Crossword full = new Crossword(5, 5, wordy);
            //System.out.println(pl.Search(full));
            Crossword mine = new Crossword(5, 5, wordy);
            // don't do this again, pichu took 18 min 19 sec to fully check
            // with print statements, 13 min 38 sec without.  No solution found
            // This is the cost of checking the entire tree of a 5x5 if there is
            // no viable solution.  Other no solution entries could be even more
            // expensive, as pichu was iterating initially over all words starting
            // with i, of which there are only 120 in this list.
            // Upon further testing, it turns out pichu does have a solution,
            // which was found in minimal time.  The problem was that, as the list
            // I used did not contain pichu, the program could never recognize it
            // as a finished grid.  Therefore, when I added pichu to my list of words,
            // the program could consider solutions.  I implemented a new setWord
            // function that adds the word automatically to the list, but this
            // should never be used other than when you know you are adding a new
            // word, as the computation involves copying over an array to increase it.
            // Perhaps I should be working with ArrayLists, but this is what I
            // have right now.
            mine.setWord(0, "aspen", true);
            System.out.println(Searcher.Search(mine));
            //On the other hand, when a word with a solution is put in, the answer
            //can be returned quite quickly.  When checking for a general solution
            //in this set, a solution was found in 30 seconds with printing
            //a different initial word without printing took 1 second to finish
            //when a solution existed.  This was for 5x5
            //System.out.println(pl.SearchAll(mine));
            //4 letter grids are much quicker.  4 seconds for both a blank
            //grid and a modified grid to finish, while a no solution grid was
            //able to check the entire tree in 30 seconds to return no solution;
            //another no solution grid took 2 min 51 sec to finish working
            //System.out.println(mine);
            //System.out.println(pl.Search(new Crossword(5,5)));
            
            //System.out.println("Search with Words test:");
            System.out.println(pl.SearchwithWords("xanax", "xerox", wordy));
            //3 letter grids are almost trivial.  With a list of 1000 words, the
            //computer takes hardly any time to either return a solution or determine
            //a lack of one
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Failure");
        }
       /*Crossword container = new Crossword(5, 5);
        container.setWordCol(0, "where");
        System.out.println(container.containsWord("where"));
        System.out.println(container.containsWord("fears"));
        container.setWordRow(2, "error");
        System.out.println(container.containsWord("error"));
        System.out.println(container.containsWord("death"));
        System.out.println(container);
        for(int i = 0; i < 5; i++)
        {
            System.out.println(container.getSpaces(i, true));
            System.out.println(container.getSpaces(i, false));
        }
        container.setLetter(4, 3, 'f');
        container.setLetter(0, 3, 'l');
        container.setLetter(3, 3, 'o');
        container.setLetter(4, 0, 'f');
        container.setLetter(3, 1, 't');
        container.setLetter(3, 4, 'y');
        System.out.println(container);
        for(int i = 0; i < 5; i++)
        {
            System.out.println(container.getSpaces(i, true));
            System.out.println(container.getSpaces(i, false));
        }
        System.out.println("Hey " + container.selectIndexRow());
        System.out.println("Hey " + container.selectIndexCol());*/
    }
    
}