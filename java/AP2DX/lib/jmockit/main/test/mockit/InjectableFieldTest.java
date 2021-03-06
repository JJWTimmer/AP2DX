/*
 * Copyright (c) 2006-2011 Rogério Liesenfeld
 * This file is subject to the terms of the MIT license (see LICENSE.txt).
 */
package mockit;

import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;

public final class InjectableFieldTest
{
   static class Foo
   {
      void doSomething(String s) { throw new RuntimeException(s); }
      int getIntValue() { return 1; }
      private Boolean getBooleanValue() { return true; }
      final List<Integer> getList() { return null; }
   }

   @Injectable Foo foo;

   @Before
   public void recordCommonExpectations()
   {
      new NonStrictExpectations()
      {{
         foo.getIntValue(); result = 123;
      }};
   }

   @After
   public void verifyExpectedInvocation()
   {
      new Verifications() {{ foo.doSomething(anyString); times = 1; }};
   }

   @Test
   public void cascadeOneLevel()
   {
      try {
         new Foo().doSomething("");
         fail();
      }
      catch (RuntimeException ignore) {}

      new NonStrictExpectations()
      {{
         foo.doSomething("test"); times = 1;
      }};

      assertEquals(123, foo.getIntValue());
      assertNull(foo.getBooleanValue());
      assertTrue(foo.getList().isEmpty());

      foo.doSomething("test");
   }

   @Test
   public void overrideExpectationRecordedInBeforeMethod()
   {
      new NonStrictExpectations()
      {{
         foo.getIntValue(); result = 45;
      }};

      assertEquals(45, foo.getIntValue());
      foo.doSomething("sdf");
   }
}
