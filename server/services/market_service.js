var	fs = require('fs'),
	JSONStream = require('JSONStream'),
	es = require('event-stream'),
	Region = require('../models/region');


exports.loadDistricts = function () {
	var file 	= __dirname+'/../data/dailyMarketPayment.json',
	 	stream 	= fs.createReadStream(file, {encoding: 'utf8'}),
    	parser 	= JSONStream.parse('*');

    return new Promise(function(resolve, reject){
        stream.pipe(parser)
        .pipe(es.mapSync(function(data) {
            Region.findOneAndUpdate(
                {name: data.Region},
                {
                    name: data.Region,
                    '$addToSet': {
                        districts: {
                            name: data.District,
                            type: data.Type
                        }

                    }
                },
                {upsert: true},
                function(err, result){
                    if(err) return reject();
                    resolve();
                }
            )
	   }))
    });
}
