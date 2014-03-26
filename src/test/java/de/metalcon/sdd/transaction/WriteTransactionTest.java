package de.metalcon.sdd.transaction;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import de.metalcon.sdd.StaticSddTestBase;
import de.metalcon.sdd.WriteTransaction;
import de.metalcon.sdd.exception.AlreadyCommitedException;
import de.metalcon.sdd.exception.EmptyTransactionException;
import de.metalcon.sdd.exception.InvalidNodeTypeException;
import de.metalcon.sdd.exception.InvalidPropertyException;
import de.metalcon.sdd.exception.InvalidRelationException;

public class WriteTransactionTest extends StaticSddTestBase {

    // TODO: Test sometimes ends fine with TransactionFailureException printed
    // in output. Test result is still ok. Will probably be fixed if
    // TransactionFailureException in Sdd is fixed.

    private static final int TEST_VALID_ACTIONS_NUM_ROUNDS = 100;

    private static final int TEST_VALID_ACTIONS_MAX_ACTIONS = 10;

    private Random random = new Random();

    private WriteTransaction tx;

    @Test
    public void testValidActions() throws InvalidNodeTypeException,
            InvalidPropertyException, AlreadyCommitedException,
            InvalidRelationException, EmptyTransactionException {
        for (int i = 0; i != TEST_VALID_ACTIONS_NUM_ROUNDS; ++i) {
            tx = sdd.createWriteTransaction();

            int numActions = random.nextInt(TEST_VALID_ACTIONS_MAX_ACTIONS) + 1;
            for (int j = 0; j != numActions; ++j) {
                performValidAction(tx, random.nextInt(NUM_VALID_ACTIONS));
            }

            tx.commit();
        }
    }

    @Test(
            expected = EmptyTransactionException.class)
    public void testEmptyCommit() throws EmptyTransactionException,
            AlreadyCommitedException {
        tx = sdd.createWriteTransaction();
        tx.commit();
    }

    @Test
    public void testAlreadyCommited1() throws EmptyTransactionException,
            InvalidNodeTypeException, InvalidPropertyException,
            InvalidRelationException, AlreadyCommitedException {
        for (int i = 0; i != NUM_VALID_ACTIONS; ++i) {
            tx = sdd.createWriteTransaction();
            performValidAction(tx, i);
            tx.commit();
            try {
                tx.commit();
                fail("Expected AlreadyCommitedException.");
            } catch (AlreadyCommitedException e) {
            }
        }
    }

    @Test
    public void testAlreadyCommited2() throws InvalidNodeTypeException,
            InvalidPropertyException, AlreadyCommitedException,
            EmptyTransactionException, InvalidRelationException {
        for (int i = 0; i != NUM_VALID_ACTIONS; ++i) {
            for (int j = 0; j != NUM_VALID_ACTIONS; ++j) {
                tx = sdd.createWriteTransaction();
                performValidAction(tx, i);
                tx.commit();
                try {
                    performValidAction(tx, j);
                    fail("Expected AlreadyCommitedException");
                } catch (AlreadyCommitedException e) {
                }
            }
        }
    }
}
