// flatten array
exports.flatten = function (arr){
    arr.reduce(function(acc, val){
        return acc.concat(Array.isArray(val) ? flatten(val) : val)
    },[])
}