# CollectionV1

## What is it?

This is a remake of api I originally made in express [Github Page](https://github.com/ShaneButtCodeNL/collection-api) to learn .NET6. This API needs to be run in order for my project [Collection APP](https://github.com/ShaneButtCodeNL/CollectionApp) to work. This api connects to an online MongoDB database That stores logins and various items I collect.
Some requests require a JWT web token to make such as adding/editing/removing entries. Authorization and authentication is handled using Java Springs Security. This project uses a mongoDB database on atlas for repositories.

## Database 

This project uses a Atlas MongoDB database to store items and user information. It uses a cluster we will call `collectionCluster` from here, a database in this cluster called `collectionDB` from here, and 2 collections one for Users called `userCollection` and for items called `itemCollection`.

### Data schema
The schemes are not optimized I use this db to make rest api when i learn new langs/frameworks.

#### user schema
```agsl
{
    _id:  ObjectId,
    userName: String,
    password: String,
    lastAccess: Date
}
```
#### item schema
```agsl
{
    _id: ObjectId,
    type: String,
    imgPath : String,
    details: Object(Map<String,Object>)
}
```
#### details schema
```agsl
{
    name: String,
    condition: String,
    genres : Array(String),
    hasCase: boolean,
    platform: String,
    publisher: String,
    releaseDate: String (month day year),
    sealed: boolean,
    volume: Integer,
    author: String,
    mediaType:String,
    limitedEdition:boolean,
    from: String,
    type:String,
    ageRestricted:boolean
}
```

## Download

### CLI

`git clone https://github.com/ShaneButtCodeNL/collectionv1.git`

### Crate .env File

This is where secrets will be stored so things like credentials are not exposed.
1. In the `resources` folder create a file called `.env`.
2. The `.env` file needs the following structure . . .
```mermaid
MONGO_DATABASE={MONGO_DB_NAME}
MONGO_USER={MONGO_USERNAME}
MONGO_PASSWORD={MONGO_PASSWORD}
MONGO_CLUSTER={CLUSTER_NAME}
JWT_SECRET={SECRET_STRING}
ADMIN_USERNAME={USERNAME_OF_ADMIN_IN_DB}
SEC_PASS={PASSWORD}
```
3. Replace values in curly brackets above with appropriate values.
4. `JWT_SECRET` is a string that will be used to sign the JWTs
5. `ADMIN_USERNAME` is the username from the user collection that is used for auth
6. `SEC_PASS` is just a string.

## Install dependencies

Since the project is built with maven in Java Spring we need to install the dependencies from the `pom.xml` file.

### CLI

1. Make sure you are in the `collectionv1` folder.
2. Run `mvnw clean install` to install Maven dependencies.
3. Done.

### IntelliJ IDE

1. Right-click the `pom.xml` file in the file tree.
2. Go to `Maven` option.
3. Click `reload project`
4. Done

## Run Project

### CLI

1. Ensure maven is installed with `mvn -v`
2. Run `mvnw spring-boot:run`
3. Done.

### IntelliJ IDE

1. open `collectionv1/src/main/java/com.collection.collectionv1/Collectionv1Application`
2. Eiter press `Shift+F10` or click green arrow on top.
3. Done.

## Testing api endpoints

All endpoints start with `http://localhost:8080/api/v1`

1. Run Project
2. Open a client like PostMan.

### Get requests
#### `/collection`
Get all items
#### `/collection/type/{item-type}`
Get all items of type `item-type`. Either ( anime, manga, figure, videogame )
#### `/collection/name/{name}`
Get all items with `name` in the name detail.

### POST Requests
Important note all POST calls except `/login` requires a Bearer token in the header under `Authorization` such as `Bearer {JWT-string}`.

#### `/login`
##### request Body 
```agsl
{
    userName,
    password
}
```
Will attempt a login and will return a JWT if successful.
#### `/collection/{item-type}`
##### request Body
```agsl
{
    name,
    imgPath,
    ...details
}
```
##### request Requires Bearer token
Will add an item to collection.

### DELETE Requests
#### `/collection/delete`
##### request Body
```agsl
{
    id
}
```
##### request requires Bearer Token
Will remove an item from collection with a matching id

### PUT Requests
#### `/collection/update`
##### request Body
```agsl
{
    id,
    ...details
}
```
##### requires a Bearer Token
Will update an items details with the given details. You only need to give the details you wish to update.

Happy Hacking.