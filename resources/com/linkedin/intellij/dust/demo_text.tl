{! You are reading the DUST template example !}
{#person test=something keya=valuea keyb="linked{expression}in"}
  Here is some HTML
  <div class="abc">this is text</div>
  {>"path/to/template"/}
  {<someInlinePartial}
  {!
    Multi-line Comment
    TODO comment
    more comments {somepath}
  !}
    {?name}
      <!--
          HTML COMMENT
      --!>
      <span>{@some.helper key="some key" text="some value"/}</span>
      <p>{key}{key|s}{key|h}{key|s|h|u} {! filters !}</p>
      {?first}
        <p>{.first}</p>
        {subscript[0]}
      {:else}
        <button>what's your name</button>
        <a href='{@pre.link key="home_page" args="id:{userId},track:{tackingCode}_suffix"/}'></a>
      {/first}
    {/name}
  {/someInlinePartial}
  {+selfClosingBlock/}
  {+block}Default Value{/block}
{/person}