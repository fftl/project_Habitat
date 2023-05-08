import 'package:flutter/material.dart';

class DockBar extends StatefulWidget {
  const DockBar({super.key});

  @override
  State<DockBar> createState() => _DockBarState();
}

class _DockBarState extends State<DockBar> {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 10),
      decoration: const BoxDecoration(
        color: Colors.white,
      ),
      child: const Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          DockBarTab(
            tabname: "report",
            tablocate: "/report",
            tabicon: Icons.library_books_rounded,
          ),
          DockBarTab(
            tabname: "reward",
            tablocate: "/reward",
            tabicon: Icons.emoji_events_rounded,
          ),
          DockBarTab(
            tabname: "home",
            tablocate: "/",
            tabicon: Icons.home,
          ),
          DockBarTab(
            tabname: "social",
            tablocate: "/social",
            tabicon: Icons.people_alt_rounded,
          ),
          DockBarTab(
            tabname: "setting",
            tablocate: "/setting",
            tabicon: Icons.settings,
          ),
          DockBarTab(
            tabname: "loading",
            tablocate: "/loading",
            tabicon: Icons.circle_outlined,
          ),
        ],
      ),
    );
  }
}

class DockBarTab extends StatelessWidget {
  final String tabname, tablocate;
  final IconData tabicon;

  const DockBarTab({
    super.key,
    required this.tabname,
    required this.tablocate,
    required this.tabicon,
  });

  @override
  Widget build(BuildContext context) {
    return TextButton(
      onPressed: () {
        Navigator.pushNamed(context, tablocate);
      },
      style: TextButton.styleFrom(
        fixedSize: const Size(60, 60),
        padding: const EdgeInsets.all(8),
        backgroundColor: Theme.of(context).primaryColor,
        shape: const RoundedRectangleBorder(
          side: BorderSide(style: BorderStyle.none),
          borderRadius: BorderRadius.all(Radius.circular(12)),
        ),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(tabicon),
          Text(tabname),
        ],
      ),
    );
  }
}
