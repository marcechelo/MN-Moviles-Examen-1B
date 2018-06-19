/**
 * SistemaOperativo.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    idSo:{type: "number"},
    nombreSo:{type: "string"},
    versionApi:{type: "number"},
    fechaLanzamiento:{type: "string"},
    pesoGigasSo:{type: "number"},
    instalado:{type: "boolean"},
    aplicaciones:{
      collection:"Aplicaciones",
      via:"sistemaOperativoId"
    }
  },

};

