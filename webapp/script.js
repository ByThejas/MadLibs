const THEMES = {
  f1: {
    displayName: "F1 Racing",
    icon: "🏎️",
    bodyClass: "theme-f1",
    optionClass: "opt-f1",
    tagline: "Lights out and away we go!",
    templates: [
      {
        prompts: ["Adjective", "Noun", "Adjective", "Verb ending in -ing", "Adjective", "A driver's name"],
        story: (a) =>
          `The ${a[0]} Scuderia Ferrari ${a[1]} looked ${a[2]} as ${a[5]}\n${a[3]} through the ${a[4]} streets of Monza.`
      },
      {
        prompts: ["A number", "Adjective", "Noun", "Verb ending in -ing", "Exclamation"],
        story: (a) =>
          `With ${a[0]} laps to go, the ${a[1]} ${a[2]} came flying past the pit wall,\n${a[3]} toward the checkered flag. "${a[4]}!" screamed the crowd.`
      }
    ]
  },
  hp: {
    displayName: "Harry Potter",
    icon: "⚡",
    bodyClass: "theme-hp",
    optionClass: "opt-hp",
    tagline: "The wand chooses the wizard...",
    templates: [
      {
        prompts: ["Adjective", "Noun", "A made-up spell", "Adjective", "A creature"],
        story: (a) =>
          `Inside the ${a[0]} halls of Hogwarts, a ${a[1]} glowed faintly.\n"${a[2]}!" shouted the young wizard, as a ${a[3]} ${a[4]} appeared from the shadows.`
      },
      {
        prompts: ["A name", "Adjective", "Noun", "Verb ending in -ing", "A place in Hogwarts"],
        story: (a) =>
          `${a[0]} crept through the ${a[1]} corridor, clutching a ${a[2]} tightly.\n${a[3]} quietly, they made their way to the ${a[4]}.`
      }
    ]
  },
  marvel: {
    displayName: "Marvel Universe",
    icon: "🛡️",
    bodyClass: "theme-marvel",
    optionClass: "opt-marvel",
    tagline: "Heroes assemble!",
    templates: [
      {
        prompts: ["A superhero name", "Adjective", "Noun", "Verb ending in -ing", "Exclamation"],
        story: (a) =>
          `${a[0]} stood atop the ${a[1]} building, gripping a ${a[2]}.\n${a[3]} into the sky, they shouted, "${a[4]}!" as the battle began.`
      },
      {
        prompts: ["Adjective", "A villain name", "Noun", "A place", "Verb ending in -ing"],
        story: (a) =>
          `The ${a[0]} ${a[1]} unleashed a ${a[2]} upon ${a[3]}.\nOnly the Avengers, ${a[4]} toward danger, could stop it now.`
      }
    ]
  }
};

const state = {
  username: "",
  themeKey: null,
  template: null
};

const screens = {
  welcome: document.getElementById("screen-welcome"),
  dashboard: document.getElementById("screen-dashboard"),
  game: document.getElementById("screen-game"),
  story: document.getElementById("screen-story")
};

function showScreen(name) {
  Object.values(screens).forEach((s) => s.classList.remove("active"));
  screens[name].classList.add("active");
}

function setBodyTheme(bodyClass) {
  document.body.className = bodyClass || "theme-default";
}

// ---------- Welcome ----------
document.getElementById("btn-welcome-continue").addEventListener("click", () => {
  const input = document.getElementById("username-input");
  const name = input.value.trim();
  const errorEl = document.getElementById("welcome-error");
  if (!name) {
    errorEl.textContent = "Please enter a username to continue.";
    return;
  }
  errorEl.textContent = "";
  state.username = name;
  renderDashboard();
  setBodyTheme("theme-default");
  showScreen("dashboard");
});

document.getElementById("username-input").addEventListener("keydown", (e) => {
  if (e.key === "Enter") document.getElementById("btn-welcome-continue").click();
});

// ---------- Dashboard ----------
function renderDashboard() {
  document.getElementById("dashboard-greeting").textContent = `${state.username}, pick a theme`;
  const grid = document.getElementById("theme-grid");
  grid.innerHTML = "";
  Object.entries(THEMES).forEach(([key, theme]) => {
    const div = document.createElement("div");
    div.className = `theme-option ${theme.optionClass}`;
    div.innerHTML = `<span class="icon">${theme.icon}</span><strong>${theme.displayName}</strong>`;
    div.addEventListener("click", () => startTheme(key));
    grid.appendChild(div);
  });
}

// ---------- Game ----------
function startTheme(key) {
  state.themeKey = key;
  const theme = THEMES[key];
  state.template = theme.templates[Math.floor(Math.random() * theme.templates.length)];

  setBodyTheme(theme.bodyClass);
  document.getElementById("game-banner-icon").textContent = theme.icon;
  document.getElementById("game-theme-title").textContent = theme.displayName;
  document.getElementById("game-tagline").textContent = theme.tagline;

  const form = document.getElementById("game-form");
  form.innerHTML = "";
  state.template.prompts.forEach((label, idx) => {
    const wrapper = document.createElement("div");
    wrapper.className = "form-field";
    wrapper.innerHTML = `
      <label for="field-${idx}">${label}</label>
      <input type="text" id="field-${idx}" required autocomplete="off" />
    `;
    form.appendChild(wrapper);
  });

  showScreen("game");
}

document.getElementById("btn-back-dashboard").addEventListener("click", () => {
  setBodyTheme("theme-default");
  renderDashboard();
  showScreen("dashboard");
});

document.getElementById("game-form").addEventListener("submit", (e) => {
  e.preventDefault();
  const theme = THEMES[state.themeKey];
  const answers = state.template.prompts.map((_, idx) =>
    document.getElementById(`field-${idx}`).value.trim() || "___"
  );
  const story = state.template.story(answers);

  document.getElementById("story-banner-icon").textContent = theme.icon;
  document.getElementById("story-title").textContent = `${theme.displayName}: Your Story`;
  document.getElementById("story-text").textContent = story;

  showScreen("story");
});

// ---------- Story screen actions ----------
document.getElementById("btn-play-again").addEventListener("click", () => {
  startTheme(state.themeKey);
});

document.getElementById("btn-change-theme").addEventListener("click", () => {
  setBodyTheme("theme-default");
  renderDashboard();
  showScreen("dashboard");
});
