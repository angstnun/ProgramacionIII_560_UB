/* Generated By:JJTree: Do not edit this line. ASTGirar.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ar.edu.ub.colchita.modelo.javacc;

public
class ASTGirar extends SimpleNode {
  public ASTGirar(int id) {
    super(id);
  }

  public ASTGirar(Colchita p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ColchitaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c8ffb407b5ff1c2e6b2857d490422cdd (do not edit this line) */
