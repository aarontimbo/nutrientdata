nutrientdata
============

dropwizard service providing access to USDA nutrient data

####Initialize Data

 1. Download flat (*.TXT) files from USDA Nutrient Database

 2. Build a jar of the project:

 <pre>mvn package</pre>

 3. Run database migrations:

 <pre>java -jar nutrientdata-x.x.x-SNAPSHOT.jar db migrate config.yml</pre>

 4. Import each USDA flat file. Example (MySQL):

 <pre>LOAD DATA INFILE '/path/to/FOOD_DES.txt'
 INTO TABLE food_description fields terminated by '^' enclosed by '~';</pre>


####File to table mapping

 * FD_GROUP.txt --> food_group

 * FOOD_DES.txt --> food_description

 * NUT_DATA.txt --> food_nutrient

 * NUTR_DEF.txt --> nutrient_definition

 * WEIGHT.txt --> food_weight


