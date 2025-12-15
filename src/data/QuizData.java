package data;

import java.util.ArrayList;
import models.QuizQuestion;

public class QuizData {

    public static ArrayList<QuizQuestion> getAllQuestions() {

        ArrayList<QuizQuestion> questions = new ArrayList<>();

        // Question 1
        questions.add(new QuizQuestion(
                "It’s Saturday morning! What is your ideal vibe?",
                new String[]{
                        "Jumping out of bed! I have a whole adventure planned. I can't sit still.",   // A
                        "Sleeping in as late as possible, then moving to the couch for snacks. Do not disturb.", // B
                        "Brunch with all the friends. I need to catch up on the gossip!", // C
                        "Getting organized. I have a to-do list to crush or a project to finish.", // D
                        "Doing something weird or niche alone, like researching aliens or reading a mystery novel." // E
                },
                "vibe"
        ));

        // Question 2
        questions.add(new QuizQuestion(
                "Your friend is feeling sad. How do you help them?",
                new String[]{
                        "I’m coming over with blankets, soup, and a shoulder to cry on.", // A
                        "I’m dragging them out of the house to distract them with fun!", // B
                        "I listen to their problem and offer logical solutions to fix it.", // C
                        "I talk to them for 4 hours straight until they are laughing again.", // D
                        "I just know exactly what’s wrong without them saying it, and I sit with them in silence." // E
                },
                "friend_help"
        ));

        // Question 3
        questions.add(new QuizQuestion(
                "Pick a 'Superpower' you wish you had.",
                new String[]{
                        "Super Speed or Flight—I want to see everything and go everywhere!", // A
                        "Invisibility or Mind Reading—I want to know secrets.", // B
                        "Time Control—So I can finally get everything done perfectly.", // C
                        "Charm/Hypnosis—I want everyone to love me and get along.", // D
                        "Teleportation—But only so I can go home instantly whenever I want." // E
                },
                "superpower"
        ));

        // Question 4
        questions.add(new QuizQuestion(
                "Be honest... what is your 'Toxic Trait'?",
                new String[]{
                        "I’m a little too loud/dramatic sometimes.", // A
                        "I can be really stubborn. Once I decide something, that’s it.", // B
                        "I’m kind of spacey and forgetful.", // C
                        "I overthink everything and can’t make a decision.", // D
                        "I get bored really easily and ghost people." // E
                },
                "toxic_trait"
        ));

        // Question 5
        questions.add(new QuizQuestion(
                "Finally, pick an aesthetic.",
                new String[]{
                        "Neon lights, glitter, bold colors, chaotic energy.", // A
                        "Dark velvet, candles, mysterious fog, old libraries.", // B
                        "Soft pastels, fairy lights, bubbles, clouds.", // C
                        "Plants, wood textures, coffee shops, minimalist design.", // D
                        "Sci-fi, metallic, outer space, futuristic." // E
                },
                "aesthetic"
        ));

        // Question 6
        questions.add(new QuizQuestion(
                "You have a group project due tomorrow. What is your role?",
                new String[]{
                        "The Leader. I’m assigning tasks and making sure we finish first.", // A
                        "The Editor. I’m fixing the typos and making the formatting look perfect.", // B
                        "The Idea Generator. I have 100 cool ideas, but someone else needs to organize them.", // C
                        "The Supporter. I’ll bring the snacks and do whatever helps the team feel less stressed.", // D
                        "The Solo Artist. I’d rather do the whole thing myself.", // E
                },
                "group_project"
        ));

        // Question 7
        questions.add(new QuizQuestion(
                "Someone starts an argument with you. How do you react?",
                new String[]{
                        "Bring it on! I’m not afraid to say exactly what I think right now.", // A
                        "I hate it. I’ll apologize or compromise just to make the fighting stop.", // B
                        "I get icy cold and use logic to dismantle their argument.", // C
                        "I shut down. I’ll give them the silent treatment until I’m ready to talk.", // D
                        "I make a joke to deflect the tension." // E
                },
                "conflict_response"
        ));

        // Question 8
        questions.add(new QuizQuestion(
                "You just found $100 on the ground! What are you doing with it?",
                new String[]{
                        "Putting it straight into my savings account.", // A
                        "Treating my friends to a round of drinks or dinner!", // B
                        "Buying that one really nice, high-quality item I’ve been eyeing.", // C
                        "Spending it on something random and fun immediately.", // D
                        "Keeping it secret and stashing it for a rainy day mystery fund." // E
                },
                "money_choice"
        ));

        // Question 9
        questions.add(new QuizQuestion(
                "Pick a destination for your dream vacation.",
                new String[]{
                        "A cabin in the woods with zero cell service and lots of food.", // A
                        "A bustling city like Tokyo or NYC.", // B
                        "Backpacking through mountains or jungles—somewhere wild!", // C
                        "A spiritual retreat or a fantasy theme park.", // D
                        "An architectural tour or a history museum trip." // E
                },
                "vacation"
        ));

        // Question 10
        questions.add(new QuizQuestion(
                "Deep question... what are you most afraid of?",
                new String[]{
                        "Being bored. I’m terrified of living a mundane, repetitive life.", // A
                        "Failure. I need to know that I’m achieving my potential.", // B
                        "Conflict/Rejection. I just want everyone to like me and be happy.", // C
                        "Betrayal. It’s hard for me to trust people.", // D
                        "Losing my freedom. I don't want to be put in a box or controlled." // E
                },
                "fear"
        ));

        return questions;
    }
}
