{!
  You are reading the DUST template example
  <todocomment>TODO comment</todocomment>
  more comments {somepath}
!}
{#person test=something keya=valuea keyb="linked{expression}in"}
  Here is some HTML
  <div class="abc">this is text</div>
  {>"path/to/template"/}
  {<someInlinePartial}
    {?name}
      <!--
          HTML COMMENT
      -->
      {! Dust Comment !}
      <span>{@some.helper key="some key" text="some value"/}</span>
      <p>{key}{key|s}{key|h}{key|s|h|u} {! filters !}</p>
      {?first}
        <p>{.first}</p>
        {subscript[0]}
      {:else}
        <button>what's your name</button>
        {#selfClosingSection/}
        <a href='{@pre.link key="home_page" args="id:{userId},track:{tackingCode}_suffix"/}'></a>
      {/first}
    {/name}
  {/someInlinePartial}

  {#items}
    {>"partials-view" item=. /}
    {@eq key=idx value=125}{/eq}
    {0}
  {/items}
  {+selfClosingBlock/}
  {+block}Default Value{/block}
{/person}