var People = require('../models/people');


exports.saveOwners = function(owners){
    return new Promise(function(resolve, reject){    
        var ids = [];
        if(typeof owners == 'string'){
            owners = JSON.parse(owners);
        }
        for(var i in owners){
            var owner = owners[i];
            var newPeople = new People;

            newPeople.firstname = owner.firstname;
            newPeople.surname = owner.surname;
            newPeople.othernames = owner.othernames;
            newPeople.dob = owner.dob;
            newPeople.birthPlace = owner.birthPlace;
            newPeople.nationality = owner.nationality;
            newPeople.nationalityType = owner.nationalityType;
            newPeople.dualCitizenship = owner.dualCitizenship;
            newPeople.ethnicity = owner.ethnicity;
            newPeople.maritalStatus = owner.maritalStatus;
            newPeople.languages = {
                spoken: owner.spokenLanguages, 
                written: owner.writtenLanguages
            };
            newPeople.phones = owner.phones;
            newPeople.email = owner.email;
            newPeople.address = {
                residential: owner.residentialAddress,
                postal: owner.postalAddress,
                work: owner.workAddress                
            };
            newPeople.region = owner.region;
            newPeople.districtType = owner.districtType;
            newPeople.homeGps = {
                lat: owner.lat,
                lng: owner.lng
            };
            newPeople.photo = owner.photo;
            newPeople.employer = owner.employer;
            newPeople.employmentStatus = owner.employmentStatus;
            newPeople.occupation = owner.occupation;
            newPeople.commencementDate = owner.commencementDate;
            newPeople.position = owner.position;
            newPeople.employmentSector = owner.employmentSector;
            newPeople.disablity = owner.disablity;
            newPeople.tin = owner.tin;
            newPeople.identification = {
                type: owner.idType,
                number: owner.idNumber,
                picture: owner.idPicture
            };
            newPeople.createdBy = owner.agentId;

            newPeople.save(function(err){
                if(err) return console.log(err);
                ids.push(newPeople._id);
                if(owners.length == i+1){
                    resolve({success: true, owners: ids});
                }
            })
        }
            
    })
    
}