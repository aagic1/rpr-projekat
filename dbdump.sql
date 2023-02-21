CREATE DATABASE  IF NOT EXISTS `freedb_e_cookbook` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `freedb_e_cookbook`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: sql.freedb.tech    Database: freedb_e_cookbook
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `recipe_id` int NOT NULL,
  `name` varchar(150) NOT NULL,
  `amount` varchar(20) DEFAULT NULL,
  `measurement_unit` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `recipe_id_idx` (`recipe_id`),
  CONSTRAINT `ingr_recipe_id` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (7,2,'12-ounce box Barilla Pronto Half-Cut Spaghetti','1',NULL),(8,2,'salt, optional to taste',NULL,'pinch'),(9,2,'ground beef or sirloin','1','pound'),(10,2,'24-ounce jar Barilla Tomato and Basil Sauce or your favorite sauce','1',NULL),(11,2,'finely chopped fresh basil for garnishing, optional',NULL,NULL),(12,2,'freshly grated Parmesan cheese for garnishing, optional',NULL,NULL),(13,3,'flour','3','cups'),(14,3,'salt','1','tsp'),(15,3,'baking powder','1','tsp'),(16,3,'oil','1/4','cup'),(17,3,'water','1','cup'),(18,4,'margarine or softened butter','175','g'),(19,4,'caster sugar','175','g'),(20,4,'eggs','3','large'),(21,4,'self-raising flour, sifted','175','g'),(22,4,'baking powder','1','tsp'),(23,4,'vanilla extract','1','tsp'),(24,4,'salt','','pinch'),(119,1,'plain flour','100','g'),(120,1,'eggs','2','large'),(121,1,'milk','300','ml'),(122,1,'sunflower or vegetable oil, plus a little for frying','1','tbsp'),(123,1,'lemon wedges to serve (optional)','',NULL),(124,1,'caster sugar to serve (optional)',NULL,NULL),(133,35,'butter, softened','225','g'),(134,35,'caster sugar','110','g'),(135,35,'plain flour','275','g'),(136,35,'cinnamon or other spices (optional)','1','tsp'),(137,35,'white or milk chocolate chips (optional)','75','g'),(138,36,'olive oil','2','tbsp'),(139,36,'onions, finely chopped','2',''),(140,36,'pumpkin or squash (try kabocha), peelede, deseeded and chopped into chunks','1','kg'),(141,36,'vegetable stock or chicken stock','700','ml'),(142,36,'double cream','150','ml'),(143,36,'olive oil','2','tbsp'),(144,36,'wholemeal seeded bread, crusts removed','4','slices'),(145,36,'pumpkin seeds','','handful'),(146,37,'package rigatoni pasta','1','(8 ounce)'),(147,37,'salt','1','tablespoon'),(148,37,'skinless, boneless chicken breast, cut into bite-sized pieces','1','pound'),(149,37,'herb-infused olive oil','1','tablespoon'),(150,37,'tapioca starch','1/4','cup'),(151,37,'Italian sesoning','1','teaspoon'),(152,37,'salt','1/4','teaspoon'),(153,37,'freshly ground black pepper','1/8','teaspoon'),(154,37,'water','3/4','cup'),(155,37,'sliced fresh mushrooms','1/3','cup'),(156,37,'chopped marinated artichoke hearts','1/4','cup'),(157,37,'chopped oil-packed sun-dried tomatoes','2','tablespoons'),(158,37,'minced garlic','3','cloves'),(159,37,'Chardonnay wine','1/3','cup'),(160,37,'heavy whipping cream','1/4','cup'),(161,37,'shredded aged Cheddar chees','2','ounces'),(162,38,'Texas toast, or thick sliced sandwich bread','8','slices'),(163,38,'unsalted butter','4','tbsp'),(164,38,'medium Cheddar cheese','4','slices'),(165,38,'Gouda cheese','4','slices'),(166,38,'Havarti cheese','4','slices');
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instruction`
--

DROP TABLE IF EXISTS `instruction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instruction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `recipe_id` int NOT NULL,
  `step` int NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `recipe_id_idx` (`recipe_id`),
  CONSTRAINT `instr_recipe_id` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instruction`
--

LOCK TABLES `instruction` WRITE;
/*!40000 ALTER TABLE `instruction` DISABLE KEYS */;
INSERT INTO `instruction` VALUES (6,2,1,'To a large pan, add the pasta, cover with 3 cups cold water, optional salt to taste, and boil over high heat until water has absorbed, about 10 minutes, but watch your pasta and cook as needed until al dente. While pasta boils, brown the ground beef.'),(7,2,2,'To a large skillet, add the ground beef and cook over medium-high heat, breaking up the meat with a spatula as it cooks to ensure even cooking.'),(8,2,3,'After beef has cooked through, add the pasta sauce, stir to combine, and cook for 1 to 2 minutes, or until heated through.'),(9,2,4,'After pasta has cooked for about 10 minutes, or until all the water has been absorbed, add the sauce over the pasta and toss to combine in the skillet or alternatively plate the pasta and add sauce to each individual plate as desired.'),(10,2,5,'Optionally garnish with basil and Parmesan to taste and serve immediately. Pasta and sauce are best warm and fresh but extra will keep airtight in the fridge for up to 5 days.'),(11,3,1,'In a large bowl, whisk together the flour and salt.'),(12,3,2,'Add the water and vegetable oil and using your hands, mix together the dough until it is a cohesive ball and all of the liquid has been absorbed. If the dough is too sticky, add more flour 1 tablespoon at a time, as needed. (The dough should be soft and Playdoh-like in texture, but not sticky.)'),(13,3,3,'Transfer the dough onto your work surface and knead it about 10 times until it becomes smooth all over.'),(14,3,4,'Divide the dough into 12 equal balls then flat each ball slightly into the shape of a hockey puck. Flour each piece of dough on both sides then set them aside and cover them with a towel.'),(15,3,5,'Heat a large nonstick sauté pan over medium-high heat.'),(16,3,6,'Roll out one piece of dough into an 8-inch circle then transfer it to the hot pan. Cook the tortilla for about 1 minute then flip it once and continue cooking it an additional 30 seconds or until golden brown spots appear on both sides. Transfer the tortilla from the pan into a towel and cover it while you roll out and cook the remaining tortillas.'),(17,4,1,'Heat the oven to 180°C (gas mark 4). Lightly grease an 18cm (7in) round cake tin with a little extra butter or margarine and cut a piece of greaseproof paper or non-stick baking parchment to fit the base of the tin.'),(18,4,2,'Put all the ingredients into a large mixing bowl and beat with a wooden spoon or a hand-held mixer for 1 minute, or until just combined. It\'s important not to beat the batter too much - just long enough to make it smooth.'),(19,4,3,'Pour or spoon the mixture into the tin, smooth the top and bake on the middle shelf of the oven for about 45-50 minutes. The cake is cooked when it looks well risen and golden; the top should spring back when lightly touched with a fingertip. Another test is to insert a skewer into the centre of the cake - it should come out clean.'),(20,4,4,'Let the cake sit in the tin for 5 minutes, then gently run a knife around the edge and turn it out onto a wire rack to cool. Serve dusted with icing sugar.'),(98,1,1,'Put 100g plain flour, 2 large eggs, 300ml milk, 1 tbsp sunflower or vegetable oil and a pinch of salt into a bowl or large jug, then whisk to a smooth batter.'),(99,1,2,'Set aside for 30 minutes to rest if you have time, or start cooking straight away.'),(100,1,3,'Set a medium frying pan or crêpe pan over a medium heat and carefully wipe it with some oiled kitchen paper.'),(101,1,4,'When hot, cook your pancakes for 1 minute on each side until golden, keeping them warm in a low oven as you go.'),(102,1,5,'Serve with lemon wedges and caster sugar, or your favourite filling. Once cold, you can layer the pancakes between baking parchment, then wrap in cling film and freeze for up to 2 months.'),(110,35,1,'Heat the oven to 190C/170C fan/gas 5. Cream the butter in a large bowl with a wooden spoon or in a stand mixer until it is soft. Add the sugar and keep beating until the mixture is light and fluffy. Sift in the flour and add the optional ingredients, if you’re using them. Bring the mixture together with your hands in a figure-of-eight motion until it forms a dough. You can freeze the dough at this point.'),(111,35,2,'Roll the dough into walnut-sized balls and place them slightly apart from each other on a baking sheet (you don’t need to butter or line it). Flatten the balls a little with the palm of your hand and bake them in the oven for around 10-12 mins until they are golden brown and slightly firm on top. Leave the cookies on a cooling rack for around 15 mins before serving.'),(112,36,1,'Heat 2 tbsp olive oil in a large saucepan, then gently cook 2 finely chopped onions for 5 mins, until soft but not coloured.'),(113,36,2,'Add 1kg pumpkin or squash, cut into chunks, to the pan, then carry on cooking for 8-10 mins, stirring occasionally until it starts to soften and turn golden.'),(114,36,3,'Pour 700ml vegetable or chicken stock into the pan and season with salt and pepper. Bring to the boil, then simmer for 10 mins until the squash is very soft.'),(115,36,4,'Pour 150ml double cream into the pan, bring back to the boil, then purée with a hand blender. For an extra-velvety consistency you can pour the soup through a fine sieve. The soup can now be frozen for up to 2 months.'),(116,36,5,'To make the croutons: cut 4 slices wholemeal seeded bread into small squares.'),(117,36,6,'Heat 2 tbsp olive oil in a frying pan, then fry the bread until it starts to become crisp.'),(118,36,7,'Add a handful of pumpkin seeds to the pan, then cook for a few mins more until they are toasted. These can be made a day ahead and stored in an airtight container.'),(119,36,8,'Reheat the soup if needed, taste for seasoning, then serve scattered with croutons and seeds and drizzled with more olive oil, if you want.'),(120,37,1,'Bring a large pot with water and 1 tablespoon salt to a boil. Cook rigatoni in the boiling water, stirring occasionally until tender yet firm to the bite, about 10 minutes. Drain and reserve pasta water.'),(121,37,2,'Meanwhile, combine tapioca starch, Italian seasoning, salt, and black pepper in a bowl. Add chicken pieces and toss to coat.'),(122,37,3,'Heat olive oil in a skillet until it shimmers. Add chicken pieces and cook until browned, about 5 minutes. You might be tempted to add more olive oil or butter, don\'t do this.'),(123,37,4,'Pour in reserved pasta water and add mushrooms, artichoke hearts, sun-dried tomatoes, and garlic. Cook about 5 minutes.'),(124,37,5,'Mix in white wine, cream and Cheddar cheese. Whisk until sauce is slightly thickened. Add rigatoni and toss to combine.'),(125,37,6,'Add rigatoni and toss to combine. Serve immediately.'),(126,38,1,'Spread 1/2 Tbsp of butter on one side of each slice of bread.'),(127,38,2,'Set a skillet over medium/low heat and place 2 slices of bread in the skillet with the butter-side-down.'),(128,38,3,'Stack cheeses on one piece of toast: cheddar, havarti, then gouda. Once the breads are golden brown, closed the sandwich with the crisp sides on the outside.'),(129,38,4,'Continue cooking until the bread is a rich golden brown, flipping once and press down lightly to help the bread stick to the cheese. Total cooking time should one 5-6 minutes. Keep the heat on medium low for the breads to toast slowly, giving your cheese a chance to fully melt and adhere to the bread.'),(130,38,5,'Once you see the cheese has melted and breads are browned, remove to a plate and cut in half diagonally to serve. Repeat with the next sandwich.');
/*!40000 ALTER TABLE `instruction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `preparation_time` int NOT NULL,
  `cook_time` int DEFAULT NULL,
  `servings` int NOT NULL,
  `notes` text,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`owner_id`),
  CONSTRAINT `owner_id` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (1,1,'Easy pancakes','Learn a skill for life with our foolproof easy crêpe recipe that ensures perfect pancakes every time',10,20,10,'Pancake notes'),(2,2,'Easy Spaghetti','This Easy Spaghetti Recipe is ready in just 15 minutes and is a hearty, comforting meal during cooler months. Pair with a side salad or steamed veggies for a full meal and enjoy!',5,10,5,NULL),(3,3,'30-Minute Homemade Flour Tortillas','Skip the store-bought tortillas and whip up a fast and fresh recipe for 30-Minute Homemade Flour Tortillas.',15,15,12,NULL),(4,4,'An easy vanilla cake recipe','This vanilla cake recipe is so easy to make, feel free to go big on the decoration. A simple dusting of icing sugar or edible flowers are our go-to options',10,50,8,NULL),(35,12,'Basic cookies','Bake a batch of these easy cookies with just five ingredients – or fewer. A perfect partner to a cup of tea, you can whip them up in no time',20,10,25,''),(36,12,'Pumpkin soup','Whip up this easy pumpkin soup as a starter for a dinner party or a light supper when you need a bit of comfort – it has a lovely silky texture',20,25,6,'Save time and effort by placing all your ingredients in a soup maker and whizzing up a delicious soup in no time. Read our review on some of the best soup makers available.'),(37,12,'Garlic Chicken Rigatoni','This pasta dish layers flavors and can be made in under 30 minutes, add a salad and crusty bread and dinner is done.',10,15,5,'You can use the oil from either the artichoke hearts, sun dried tomatoes or both instead of the herb-infused olive oil.\n\nYou can substitute flour for tapioca starch but I just like the silky texture the tapioca starch gives.\n\nI used cave-aged Cheddar cheese for this recipe that I buy at a specialty store, but you can use Cabot extra sharp Cheddar cheese instead.'),(38,12,'The Ultimate Grilled Cheese Sandwich','Learn how to make the best Grilled Cheese sandwich with a crisp, buttery exterior and gooey cheese center.',5,25,4,'');
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(45) NOT NULL,
  `about` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aagic1','aagic1@etf.unsa.ba','agicahmed','Cooking enthusiast'),(2,'anon','anon@gmail.com','anonanon2',NULL),(3,'patrick','patrick1@gmail.com','patrickbate','I am not here'),(4,'mujo','mujo.o@gmail.com','omujo',''),(12,'gost123','gost@gmail.com','gostrpr',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-21 16:53:16
