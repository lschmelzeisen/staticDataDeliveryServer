package de.metalcon.sdd.transaction;

import org.junit.Test;

import de.metalcon.sdd.exception.AlreadyCommitedException;
import de.metalcon.sdd.exception.InvalidNodeTypeException;
import de.metalcon.sdd.exception.InvalidRelationException;

public class SetRelationsTest extends ActionTestBase {

    private static final long NODE_ID = 1L;

    private static final long[] TO_IDS1 = {
        2L
    };

    private static final long[] TO_IDS2 = {
        2L, 3L
    };

    @Test
    public void testValidArguments() throws InvalidNodeTypeException,
            InvalidRelationException, AlreadyCommitedException {
        tx.setRelations(NODE_ID, "Node1", "relation1", TO_IDS1);
        tx.setRelations(NODE_ID, "Node2", "relation1", TO_IDS1);

        tx.setRelations(NODE_ID, "Node1", "relation1", TO_IDS2);
        tx.setRelations(NODE_ID, "Node2", "relation1", TO_IDS2);
    }

    @Test(
            expected = InvalidNodeTypeException.class)
    public void testInvalidNodeType() throws InvalidRelationException,
            InvalidNodeTypeException, AlreadyCommitedException {
        tx.setRelations(NODE_ID, "UnkownNodeType", "relation1", TO_IDS1);
    }

    @Test(
            expected = InvalidRelationException.class)
    public void testInvalidRelation1() throws InvalidRelationException,
            InvalidNodeTypeException, AlreadyCommitedException {
        tx.setRelations(NODE_ID, "Node1", "UnkownRelation", TO_IDS1);
    }

    @Test(
            expected = InvalidRelationException.class)
    public void testInvalidRelation2() throws InvalidRelationException,
            InvalidNodeTypeException, AlreadyCommitedException {
        tx.setRelations(NODE_ID, "Node1", "relation2", TO_IDS1);
    }

}
