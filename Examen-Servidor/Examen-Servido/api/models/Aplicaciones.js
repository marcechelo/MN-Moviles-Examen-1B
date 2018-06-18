/**
 * Aplicaciones.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    id:{
      type: "number"
    },
    nombre:{
      type: "string"
    },
    pesoGigas:{
      type: "number"
    },
    version:{
      type: "number"
    },
    url:{
      type: "string"
    },
    fechaLanzamiento:{
      type: "string"
    },
    costo:{
      type: "number"
    },
    sistemaOperativoId:{
      model:"SistemaOperativo"
    },


  },

};

