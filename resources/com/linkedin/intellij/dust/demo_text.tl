{! You are reading the DUST template example !}
{#person test=something keya=valuea keyb="linked{expression}in"}
  Here is some HTML
  <div class="abc">this is text</div>
  {>"path/to/template"/}
  {<someInlinePartial}
    {!
      Comment
      block
    !}
    {?name}
        <span>{@some.helper key="some key" text="some value"/}</span>
        <p>{key}{key|s}{key|h}{key|s|h|u} {! filters !}</p>
        {?first}
          <p>{.first}</p>
        {:else}
          <button>what's your name</button>
        {/first}
    {/name}
  {/someInlinePartial}
  {+block}Default Value{/block}
{/person}