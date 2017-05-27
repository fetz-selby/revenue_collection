// flatten array
exports.flatten = function (arr){
    arr.reduce(function(acc, val){
        return acc.concat(Array.isArray(val) ? flatten(val) : val)
    },[])
}


exports.getdateFromNow = function(days) {
	var today = new Date();
	today.setHours(0,0,0,0);
	return new Date(today.setDate(today.getDate() - days));
}