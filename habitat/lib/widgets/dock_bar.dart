import 'package:flutter/material.dart';
import 'package:habitat/controller/report_controller.dart';
import 'package:get/get.dart';

class DockBar extends StatefulWidget {
  const DockBar({super.key});

  @override
  State<DockBar> createState() => _DockBarState();
}

class _DockBarState extends State<DockBar> {
  final reportController = Get.put(ReportController());

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 10),
      decoration: const BoxDecoration(
        color: Colors.white,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          GestureDetector(
            onTap: () {
              reportController.goalUpdate();
              reportController.dailyUpdate();
              reportController.weeklyIntakeUpdate();
              reportController.monthlyIntakeUpdate();
            },
            child: const DockBarTab(
              tabname: "report",
              tablocate: "/report",
              tabicon: Icons.my_library_books_outlined,
            ),
          ),
          DockBarTab(
            tabname: "reward",
            tablocate: "/reward",
            tabicon: Icons.emoji_events_outlined,
          ),
          DockBarTab(
            tabname: "home",
            tablocate: "/main",
            tabicon: Icons.home_rounded,
          ),
          DockBarTab(
            tabname: "social",
            tablocate: "/social",
            tabicon: Icons.people_alt_outlined,
          ),
          DockBarTab(
            tabname: "setting",
            tablocate: "/setting",
            tabicon: Icons.settings_outlined,
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
        padding: const EdgeInsets.all(0),
        // backgroundColor: Theme.of(context).primaryColor,
        shape: const RoundedRectangleBorder(
          side: BorderSide(style: BorderStyle.none),
          borderRadius: BorderRadius.all(Radius.circular(12)),
        ),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            tabicon,
            size: 38,
          ),
          Text(tabname),
        ],
      ),
    );
  }
}
