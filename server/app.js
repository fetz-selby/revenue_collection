var express = require('express'),
    cookieParser = require('cookie-parser'),
    bodyParser = require('body-parser'),
    multer = require('multer'),
    session = require('express-session'),
    port = process.env.PORT || 8002,
    mongoose = require('mongoose'),
    modelInitializer = require('./services/model_service'),
    helper = require('./services/helper_service'),
    logger = require('morgan'),
    expressValidator = require('express-validator'),
    dbConfig = require('./config');

var app = express(),

//Define Mongo Instance
pool = {};
mongoose.Promise = global.Promise;

//Init DB instance
mongoose.connect('mongodb://'+dbConfig.config.db_instance);

//Init Schema Models
var models = require('./services/model_service');

//uncomment to reload markets to db
// helper.loadMarkets();

//Instantiating all routes
var agentsRoute     = require('./routes/agents_router')(pool),
    analyticsRoute  = require('./routes/analytics_router')(pool),
    authRoute       = require('./routes/auth_router')(pool),
    marketsRoute    = require('./routes/markets_router')(pool),
    sellersRoute    = require('./routes/sellers_router')(pool),
    usersRoute      = require('./routes/users_router')(pool);
    paymentsRoute   = require('./routes/payments_router')(pool);


app.use(express.static('public', {extensions: ['html', 'htm']}));
//Set middlewares
//app.use(bodyParser.urlencoded({extended: true}));
//app.use(bodyParser.json());
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
app.use(cookieParser());
app.use(expressValidator([]));
app.use(session({resave:true, saveUninitialized: true,
                secret: 'thequickbrownfoxjumpedoverthelazydogs',
                cookieName: 'session',
                duration: 30*60*1000,
                activeDuration: 5*60*1000,
                httpOnly: true,
                cookie: {secure: false }}));

//CORS enabling
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
  next();
});

//logging
app.use(logger('dev'));

//Disable cache
app.use(function (req, res, next) {
    res.header('Cache-Control', 'private, no-cache, no-store, must-revalidate');
    res.header('Expires', '-1');
    res.header('Pragma', 'no-cache');
    next();
});

app.use('/eghana/revenue/api/auth', authRoute.router);
app.use('/eghana/revenue/api/agents', agentsRoute.router);
app.use('/eghana/revenue/api/analytics', analyticsRoute.router);
app.use('/eghana/revenue/api/markets', marketsRoute.router);
app.use('/eghana/revenue/api/users', usersRoute.router);
app.use('/eghana/revenue/api/sellers', sellersRoute.router);
app.use('/eghana/revenue/api/payments', paymentsRoute.router);

app.get('/eghana', function(req, res){
    res.status(200).send('Please check API documentation');
});

app.get('/', function(req, res){
    res.status(200).send('Please check API documentation');
});

app.get('/eghana/revenue', function(req, res){
    res.status(200).send('Please check API documentation');
});

app.get('/eghana/revenue/api', function(req, res){
    res.status(200).send('Please check API documentation');
});

app.listen(port, function(){
    console.log('Running on PORT '+port);

    //Init all events
    initAllEvents();
});

process.on("unhandledRejection", function(reason, p){
    console.log("Unhandled", p); // log all your errors, "unsuppressing" them.
//    throw(reason);

});

var initAllEvents = function(){

}

module.exports = app;
