/**
 * SistemaOperativo.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    idSO:{
      require,
      autoIncrement,
      type: "number"
    },
    nombreSO:{
      type: "string"
    },
    versionApi:{
      type: "number"
    },
    fechaLanzamiento:{
      type: "string"
    },
    pesoGigas:{
      type: "number"
    },
    instalado:{
      type: "boolean"
    },

    aplicaciones:{
      collection:"Aplicaciones",
      via:"sistemaOperativoId"
    }

  },

};

