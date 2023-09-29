import java.util.*;

class LyricLink {
    static User activeUser;
    static HashSet<User> users = new HashSet<>();
    static Song First = null;
    static Scanner sc = new Scanner(System.in);
    static Queue<Song> ownChoice = new ArrayDeque<>();
    static HashSet<Song> choiceItem = new HashSet<>();

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Boolean b = true;
        if (users.size() == 0) {
            inbuildUser();
        }
        addMusics();
        while (b) {
            System.out.println("1.Sign Up\n2.Log In\n3.Exit");
            System.out.print("Enter Your Choice : ");
            switch (sc.nextInt()) {
                case 1:
                    sc.nextLine();
                    signUp();
                    main(args);
                    break;
                case 2:
                    sc.nextLine();
                    sc.nextLine();
                    login();
                    main(args);
                    break;
                case 3: {
                    b = false;
                    System.exit(0);
                    break;
                }
            }
        }
    }

    static void inbuildUser() {
        User user1 = new User("Moksha Shah", 6969696969l, "m.j", "140899");
        User user = new User("TIRTH SHAH", 7043631192l, "tirth.shah", "123456");
        User user2 = new User("Hetavi Shah", 79907171818l, "hetavi.shah", "hs0508");

        users.add(user);
        users.add(user1);
        users.add(user2);

    }

    static boolean login() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            System.out.print("Enter User Id : ");
            String userId = sc.nextLine();
            for (User user : users) {

                if (user.userId.equals(userId)) {
                    System.out.print("Enter Password : ");
                    String password = sc.next();

                    if (user.password.equals(password)) {
                        if (activeUser != null) {
                            if (activeUser != user) {
                                ownChoice.clear();
                            }
                        }
                        activeUser = user;
                        listenMusic();
                        return true;
                    }
                }
            }
            System.out.println("Invalid User Id or password.");
            System.out.println();
        }
        return false;

    }

    static void signUp() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name : ");
        String name = sc.nextLine();
        for (;;) {
            System.out.print("Enter Mobile Number : ");
            long mobileNumber = sc.nextLong();
            if (validateMobileNumber(mobileNumber)) {
                for (;;) {

                    System.out.print("Create UserId : ");
                    String userId = sc.next();
                    System.out.println();
                    if (validateUserId(userId)) {
                        for (;;) {

                            System.out.println(
                                    "Password must be of 6 characters only.\nNo spaces are allowed in the password.\n");
                            System.out.print("Enter password : ");
                            String password = sc.next();
                            System.out.println();

                            if (validatePassword(password)) {
                                User user = new User(name, mobileNumber, userId, password);
                                users.add(user);
                                if (activeUser != null) {
                                    if (activeUser != user) {
                                        ownChoice.clear();
                                    }
                                }
                                activeUser = user;
                                listenMusic();
                                return;
                            } else {
                                System.out.println("Invalid password type.\nTry again\n");
                            }
                        }

                    } else {
                        System.out.println("Sorry, This userId is already taken");
                        System.out.println("Try Entering With some Other Id");
                        System.out.println();
                        System.out.println();
                    }
                }

            } else {
                System.out.println("Enter A Valid mobile number.");
            }
        }
    }

    static boolean validateMobileNumber(long mobileNumber) {// To check if mobile number entered is valid or not.
        if (mobileNumber >= 1000000000l && mobileNumber <= 9999999999l) {
            return true;
        }
        return false;
    }

    static boolean validateUserId(String userId) {
        for (User user : users) {
            if (user.userId.equals(userId))
                return false;
        }
        return true;

    }

    static boolean validatePassword(String password) {
        if (password.toCharArray().length != 6)
            return false;
        else if (password.contains(" "))
            return false;

        return true;
    }

    static void listenMusic() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1. Listen Music Randomly");
        System.out.println("2. Search");
        System.out.println("3. Add to Queue");
        System.out.println("4. Listen Your Queue");
        System.out.println("5. Display Songs Details");
        System.out.println("6. Display Your Favourite");
        System.out.println("7. Exit");
        System.out.println("Enter your Choice");
        switch (sc.nextInt()) {
            case 1:
                listenMusicRandomly();
                listenMusic();
                break;
            case 2:
                search();
                listenMusic();
                break;
            case 3:
                ownChoice = new ArrayDeque<>();
                System.out.println();
                l1: for (;;) {
                    displayAllDetails();
                    System.out.println("Enter Song Number To Be Added To Your Queue");
                    int j = sc.nextInt();

                    boolean b = addToQueue(j);
                    if (!b)
                        System.out.println("Enter Valid Song Number To Be Added To Your Queue");
                    if (b)
                        System.out.println("Song added To Queue Successfully.");
                    break l1;
                }
                listenMusic();
                break;
            case 4:
                l2: if (ownChoice.size() == 0) {
                    System.out.println("No Song In A Queue");
                    break l2;
                }
                for (Song Songs : ownChoice) {
                    displayLyrics(Songs);
                }
                listenMusic();
                break;
            case 5:
                displayAllDetails();
                listenMusic();
                break;
            case 6:
                boolean b = false;
                Song temp = First;
                System.out.println("        YOUR FAVOURITES");
                while (temp != null) {
                    if (temp.data.song_likes == 1) {
                        displayLyrics(temp);
                        b = true;
                    }
                    temp = temp.next;
                }
                li: if (!b) {
                    System.out.println("There is no Song In Your Favourite");
                    break li;
                }
                listenMusic();

                break;
            case 7:
                sc.nextLine();
                return;
        }
    }

    static void displayAllDetails() throws InterruptedException {
        Song temp = First;
        int i = 1;
        while (temp != null) {
            System.out.println("\n\n");
            System.out.println("------" + i + "------");
            System.out.println("Song Title : " + temp.data.song_title);
            System.out.println("Artist : " + temp.data.singer_name);
            System.out.println("Year : " + temp.data.song_year);
            System.out.println("Language : " + temp.data.song_type);
            temp = temp.next;
            i++;
        }
        System.out.println();
    }

    static boolean addToQueue(int j) {
        Song temp = First;
        int i = 1;
        System.out.println("\n\n");
        while (temp != null) {
            if (i == j) {
                ownChoice.add(temp);
                return true;
            }
            temp = temp.next;
            i++;
        }
        return false;
    }

    static void search() throws InterruptedException {
        String name;
        System.out.println("\n1. Search By Name");
        System.out.println("2. Search By Singer");
        System.out.println("3. Search By Lyrics");
        System.out.println("4. Search By Language");
        System.out.println("5. Search By Year");
        System.out.println("6. Search By Decade");
        System.out.println("7. Exit");
        System.out.print("Enter Your Choice : ");
        switch (sc.nextInt()) {
            case 1:
                System.out.println();
                sc.nextLine();
                System.out.print("Enter Song Name:");
                name = sc.nextLine();
                searchByName(name);
                search();
                break;
            case 2:
                System.out.println();
                sc.nextLine();
                System.out.print("Enter Singer Name :");
                name = sc.nextLine();
                searchBySinger(name);
                search();
                break;
            case 3:
                System.out.println();
                sc.nextLine();
                System.out.print("Enter Lyrics :");
                name = sc.nextLine();
                searchByLyrics(name);
                search();
                break;
            case 4:

                System.out.println("\n1. Hindi");
                System.out.println("2. English");
                System.out.println("3. Your choice Language:");
                System.out.print("Enter Your Choice : ");
                switch (sc.nextInt()) {
                    case 1:
                        name = "Hindi";
                        sc.nextLine();
                        searchByLanguage(name);
                        search();
                        break;
                    case 2:
                        name = "English";
                        sc.nextLine();
                        searchByLanguage(name);
                        search();
                        break;
                    case 3:
                        sc.nextLine();
                        System.out.println("Enter Your Choice Langage");
                        name = sc.nextLine();
                        searchByLanguage(name);
                        search();
                        break;
                }
                break;
            case 5:
                System.out.println();
                System.out.print("Enter Year :");
                int year = sc.nextInt();
                searchByYear(year);
                search();
                break;
            case 6:
                System.out.println("\n1. 1990-1999");
                System.out.println("2. 2000-2009");
                System.out.println("3. Your choice Decade:");
                System.out.print("Enter Your Choice : ");
                switch (sc.nextInt()) {
                    case 1: {
                        int startYear = 1990, endYear = 1999;
                        searchByDecade(startYear, endYear);
                        search();
                        break;
                    }
                    case 2: {
                        int startYear = 1990, endYear = 1999;
                        searchByDecade(startYear, endYear);
                        search();
                        break;
                    }
                    case 3: {
                        int startYear;
                        for (;;) {
                            System.out.println("Enter Starting Year of Decade");
                            startYear = sc.nextInt();
                            if (startYear % 10 == 0)
                                break;
                            else {
                                System.out.println(
                                        "Sorry Invalid Input \n Enter Year Ending with '0'\n Example:-1990,2000");
                            }
                        }
                        searchByDecade(startYear, startYear + 9);
                        search();
                        break;
                    }
                }
                break;
            case 7:
                System.out.println("\n Thank You!");
                sc.nextLine();
                return;
        }

    }

    static void searchByDecade(int startYear, int endYear) throws InterruptedException {
        Song temp = First;
        while (temp != null) {
            if (temp.data.song_year >= startYear && temp.data.song_year <= endYear) {
                System.out.println();
                choiceItem.add(temp);
            }
            temp = temp.next;
        }
        displayChoiceItem("Years");
    }

    static void searchByYear(int year) throws InterruptedException {
        Song temp = First;
        while (temp != null) {
            if (temp.data.song_year == year) {
                System.out.println();
                choiceItem.add(temp);
            }
            temp = temp.next;
        }
        displayChoiceItem("Years");
    }

    static void searchByLanguage(String name) throws InterruptedException {
        Song temp = First;
        while (temp != null) {
            if (temp.data.song_type.toLowerCase().contains(name.toLowerCase())) {
                System.out.println();
                choiceItem.add(temp);
            }
            temp = temp.next;
        }
        displayChoiceItem("Language");

    }

    static void searchByLyrics(String name) throws InterruptedException {
        Song temp = First;
        while (temp != null) {
            if (temp.data.song_Lyrics.toLowerCase().contains(name.toLowerCase())) {
                System.out.println();
                choiceItem.add(temp);
            }
            temp = temp.next;
        }
        displayChoiceItem("Songs");

    }

    static void searchBySinger(String name) throws InterruptedException {
        Song temp = First;
        System.out.println();
        while (temp != null) {
            if (temp.data.singer_name.toLowerCase().contains(name.toLowerCase())) {
                choiceItem.add(temp);
            }
            temp = temp.next;
        }
        displayChoiceItem("Singers");
    }

    static void searchByName(String name) throws InterruptedException {
        Song temp = First;
        while (temp != null) {
            if (temp.data.song_title.toLowerCase().contains(name.toLowerCase())) {
                choiceItem.add(temp);
            }
            temp = temp.next;
        }
        displayChoiceItem("Songs");
        choiceItem.clear();
    }

    static void displayChoiceItem(String s) throws InterruptedException {
        int i = 1;
        System.out.println("\n--------Related Songs--------\n");
        for (Song songs : choiceItem) {
            System.out.println(i + ". " + songs.data.song_title);
            ++i;
        }
        System.out.println(i + ". Exit");
        System.out.print("Enter Your Choice : ");
        int j = sc.nextInt();
        if (j != i && j < i) {
            playChoiceItem(j, s);
        }
        if (j == i) {
            return;
        }
        if (j > i) {
            System.out.println("Enter Valid Choice.");
            displayChoiceItem(s);
        }
    }

    static void playChoiceItem(int j, String s) throws InterruptedException {
        int c = 1;
        if (choiceItem.size() != 0) {
            for (Song songs : choiceItem) {
                if (j == c) {
                    displayLyrics(songs);
                    addFavBySearch(songs);
                    displayChoiceItem(s);
                }
                c++;
            }
        }
    }

    static void addFavBySearch(Song temp) {
        boolean b = true;
        li: while (b) {
            System.out.println("\n1. Add To Favourite");
            System.out.println("2. Exit");
            System.out.println("Enter Your Choice");
            switch (sc.nextInt()) {
                case 1:
                    temp.data.song_likes = 1;
                    break li;
                case 2:
                    b = false;
                    break;
            }
        }
    }

    static void listenMusicRandomly() throws InterruptedException {
        Song temp = First;
        int i = 1;
        L1: while (temp != null) {
            displayLyrics(temp);
            boolean b = whatToDoNext(temp, i);
            if (!b)
                break L1;
            temp = temp.next;
            i++;

        }
    }

    static void displayLyrics(Song temp) throws InterruptedException {
        System.out.println();
        System.out.println("--------" + temp.data.song_title + "--------\n");
        String[] s = temp.data.song_Lyrics.split("\n");
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
            Thread.sleep(500);
        }
        System.out.println();
    }

    static boolean whatToDoNext(Song temp, int j) throws InterruptedException {
        boolean break1;
        System.out.println("\n1. Play Next");
        System.out.println("2. Play Previous");
        System.out.println("3. Loop");
        System.out.println("4. Add To queue");
        System.out.println("5. Add Song To Favourite");
        System.out.println("6. Exit");

        System.out.println("Enter Your Choice");
        switch (sc.nextInt()) {
            case 1:
                return true;
            case 2:
                if (temp.prev != null)
                    playPrev(temp);
                else {
                    System.out.println("No Song is there to Play");
                }
                whatToDoNext(temp, j);
                return true;
            case 3:
                displayLyrics(temp);
                whatToDoNext(temp, j);
                return true;
            case 4:
                ownChoice.add(temp);
                System.out.println("Added To Queue Successfully.");
                whatToDoNext(temp, j);
                return true;
            case 5:
                addFav(temp);
                System.out.println("Added To Favourite Successfully.");
                whatToDoNext(temp, j);
                return true;
            case 6:
                return false;
        }
        return false;
    }

    static void addFav(Song temp) {
        temp.data.song_likes = 1;
    }

    static void playPrev(Song temp) throws InterruptedException {
        temp = temp.prev;
        displayLyrics(temp);
    }

    void insertAtLast(details d) {
        Song n = new Song(d);
        if (First == null) {
            First = n;
        } else {
            Song last = First;
            while (last.next != null) {
                last = last.next;
            }
            last.next = n;
            n.prev = last;
        }
    }

    void display() {
        if (First == null) {
            System.out.println("Empty");
        } else {
            Song temp = First;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            System.out.println();
        }
    }

    static void addMusics() {
        LyricLink l = new LyricLink();
        if (First == null) {
            ArrayList<details> songs = new ArrayList<>();
            songs.add(new details("Arijit Singh", "Tum Kya Mile", "Hindi", "Tum Kya Mile\n" +
                    "Tum Kya Mile\n" +
                    "Hum Na Rahe Hum\n" +
                    "Tum Kya Mile\n" +
                    "\n" +
                    "Jaise Mere\n" +
                    "Dil Mein Khile\n" +
                    "Phagun Ke Mausam\n" +
                    "Tum Kya Mile", 2023, 0));
            songs.add(new details("Neha Kakkar", "Mile ho Tum hamko", "Hindi", "Mile ho tum humko\n" + //
                    "Bade naseebon se\n" + //
                    "Churaya hai maine\n" + //
                    "Kismat ki lakeeron se \n" + //
                    "\n" + //
                    "Teri mohabbat se saansein mili hain\n" + //
                    "Sada rehna dil mein kareeb hoke", 2016, 0));
            songs.add(new details("Arijit Singh", "Tum Hi Ho", "Hindi", "Hum tere bin ab reh nahi sakte\n" + //
                    "Tere bina kya wajood mera \n" + //
                    "\n" + //
                    "Tujh se juda gar ho jaayenge\n" + //
                    "To khud se hi ho jaayenge juda", 2013, 0));
            songs.add(new details("Rashid Ali", "Kabhi Kabhi Aditi", "Hindi",
                    "Kabhi kabhi aditi zindagi mein yuhi koi apna lagta hai,\n" + //
                            "Kabhi kabhi aditi wo bichad jaaye to ek sapna lagta hai,\n" + //
                            "Aise mein koi kaise apne aasuon ko behne se roke?\n" + //
                            "Aur kaise koi soch le everythings gonna be ok?",
                    2008, 0));

            songs.add(new details("AK Rok ", "Yadav Brand 2", "Punjabi", "Maran ki baat kare tere ke\n" + //
                    "Aukaat bahar re chora rao sahab ke\n" + //
                    "Mazak thodi hai,\n" + //
                    "Re jonsa bhi mahare beta haath laavega \n" + //
                    "Saala pachtavega na sukh paavega sun kaan kholke\n" + //
                    "Ho gulaami na hove dekh kise saale ki \n" + //
                    "Sun tere yaar rao sahab teee...", 2022, 0));
            songs.add(new details("Badshah", "Wakhara Song", "Punjabi", "Ki ae Gucci, Armani\n" + //
                    "Pichhe roldi jawani\n" + //
                    "Check kardi brand'aan wale tag ni\n" + //
                    "Aaja dassan tainu soniye ni fashion ki hunda\n" + //
                    "Tere yaar da ta Wakhra Swag ni", 2015, 0));
            songs.add(new details("Sidhu Moosawala", "295", "Punjabi", "Dass putt tera head down kaston\n" + //
                    "Changa bhala hass si maun kaston\n" + //
                    "Haan jehde darwajje vich\n" + //
                    "", 2021, 0));
            songs.add(new details("A P Dhillon", "With You", "Punjabi", "Teriyan adavaan\n" + //
                    "Teriyan adavaan munda maar sutteya\n" + //
                    "Tu kahda dil lutteya, tu mainu chhadeya na kakh da\n" + //
                    "Tu mainu chhadeya na kakh da", 2023, 0));

            songs.add(new details("Amit Trivedi", "Moti Verana", "Gujarati", "Moti veraana chowk ma aavya Ambe Maa\n" + //
                    "Chowk ma jagmag thaaye re aavya Ambe Maa\n" + //
                    "Moti veraana chowk ma aavya Ambe Maa\n" + //
                    "Chowk ma jagmag thaaye re aavya Ambe Maa", 2022, 0));
            songs.add(new details("Sachin Jigar", "Meri Ladki Re", "Gujarati", "Teri Laadki Main, Laadki Main\n" + //
                    "Teri Laadki Main, Laadki Main\n" + //
                    "Teri Laadki Main Chodungi Na Tera Hath\n" + //
                    "", 2022, 0));
            songs.add(new details("Sonu Nigam", "Papa Pagli", "Gujarati",
                    "Pa pa pagli, te kidhi, Jhali ne maro hath\n" + //
                            "Jiv ni dhagli, me aakhi, rakhi chhe tare kaaj\n" + //
                            "Ho ho ho…. mara vhal, Ho ho ho..\n" + //
                            "Pa pa pagli, te kidhi, Jhali ne maro hath\n" + //
                            "Jiv ni dhagli, me aakhi, rakhi chhe tare kaaj...",
                    2019, 0));
            songs.add(new details("Kirtidan Gadvi", "Khalasi", "Gujarati", "Gotilo, tame gotilo gotilo gotilo\n" + //
                    "Gotilo, tame gotilo gotilo gotilo\n" + //
                    "Nathi je majhaama\n" + //
                    "Khaali vaavataa dhajaa maa\n" + //
                    "Evo haad no pravaasi gotilo", 2022, 0));

            songs.add(new details("Ed Sheeran", "Perfect", "English", "I found a love, for me\n" + //
                    "Darling, just dive right in and follow my lead\n" + //
                    "Well, I found a girl, beautiful and sweet\n" + //
                    "Oh, I never knew you were the someone waiting for me", 2017, 0));
            songs.add(new details("Ed Sheeran", "Shape of You", "English",
                    "The club isn't the best place to find a lover\n" + //
                            "So the bar is where I go\n" + //
                            "Me and my friends at the table doing shots\n" + //
                            "Drinking fast and then we talk slow",
                    2017, 0));
            songs.add(new details("Chain Smokers", "Closers", "English",
                    "Hey, I was doing just fine before I met you\n" + //
                            "I drink too much and that's an issue, but I'm okay\n" + //
                            "Hey, you tell your friends it was nice to meet them\n" + //
                            "But I hope I never see them again",
                    2016, 0));
            songs.add(new details("Stephen Sanchez ", "Until I Found You", "English",
                    "Georgia, wrap me up in all your\n" + //
                            "I want you in my arms\n" + //
                            "Oh, let me hold you\n" + //
                            "I'll never let you go again like I did\n" + //
                            "Oh, I used to say",
                    2022, 0));
            Collections.shuffle(songs);
            for (details d : songs) {
                l.insertAtLast(d);
            }
        }
    }

    class Song {
        Song next, prev;
        details data;

        public Song(details data) {
            this.data = data;
        }

        public Song() {
        }

    }
}

class details {
    String singer_name, song_title, song_type, song_Lyrics;
    int song_year, song_likes;

    public details(String singer_name, String song_title, String song_type, String song_Lyrics, int song_year,
            int song_likes) {
        this.singer_name = singer_name;
        this.song_title = song_title;
        this.song_type = song_type;
        this.song_Lyrics = song_Lyrics;
        this.song_year = song_year;
        this.song_likes = song_likes;
    }
}

class User {
    String name, userId, password;
    long mobileNumber;

    public User(String name, long mobileNumber, String UserId, String password) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.userId = UserId;
        this.password = password;
    }
}