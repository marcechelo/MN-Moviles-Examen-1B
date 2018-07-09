/**
 * Usuario.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    tipo:{type: "number"},
    username:{type: "string"},
    password:{type: "string"},

    sistemaOperativo:{
      collection: 'SistemaOperativo',
      via: 'usuario'
    },

    ordenes:{
      collection: 'Ordenes',
      via: 'usuario'
    }

  },

};

